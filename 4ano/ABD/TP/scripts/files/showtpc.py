#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# Simple analysis of Escada TPC-C and PHARM/UW-Madison TPC-W results.
# by José Pereira, jop@di.uminho.pt.

import scipy.stats as st
import numpy as np
import getopt, sys

try:
	import matplotlib.pyplot as plt
	canPlot = True
except:
	canPlot = False


class Run(object):
	def bounds(self, u, i):
		self.RUidx = int(len(self.end_times)*u)
		self.RU = self.end_times[self.RUidx]/1000
		self.MIidx = int(len(self.end_times)*i)
		self.MI = self.end_times[self.MIidx]/1000-self.RU

	def throughput(self):
		et = sorted(self.end_times)
		iat = [float(f - i)/1000 for i,f in zip(et,et[1:])]
		return 1/np.mean(iat[self.RUidx:self.MIidx])
		
	def responseTime(self):
		responseTime = [float(f - i)/1000 for i,f in zip(self.start_times,self.end_times)]
		return np.mean(responseTime[self.RUidx:self.MIidx])

	def abortRate(self):
		na = len([t for t in self.abort_times if t>=self.RU*1000 and t<(self.MI+self.RU)*1000])
		nc = self.MIidx - self.RUidx
		return float(na)/(na+nc)

	def plot(self):
		responseTime = [float(f - i)/1000 for i,f in zip(self.start_times,self.end_times)]
		et = sorted(self.end_times)
		iat = [float(f - i)/1000 for i,f in zip(et,et[1:])]
		xx = [float(v)/1000 for v in self.end_times]

		fig = plt.figure()

		ax = fig.add_subplot(221, title='Response time', xlabel='run-time (s)', ylabel='response time (s)')
		plt.ylim(ymax=np.amax(responseTime[self.RUidx:self.MIidx])*1.1)
		ax.plot(xx, responseTime, linestyle='None', marker='.')
		ax.axvline(x=self.RU, color='r',label='interval')
		ax.axvline(x=self.RU+self.MI, color='r')
		m = np.mean(responseTime[self.RUidx:self.MIidx])
		p = st.scoreatpercentile(responseTime[self.RUidx:self.MIidx],90)
		ax.axhline(y=m,color='g',label='RT %0.03fs'%(m))
		ax.legend()

		ax = fig.add_subplot(222, title='Response time in interval', xlabel='response time (s)')
		ax.hist(responseTime[self.RUidx:self.MIidx], 30)
		ax.axvline(x=m,color='g', label='RT %0.03fs'%(m))
		ax.axvline(x=p,color='m', label='90%% RT %0.03fs'%(p))
		ax.legend()

		ax = fig.add_subplot(223, title='Inter-arrival time', xlabel='run-time (s)', ylabel='inter-arrival (s)')
		plt.ylim(ymax=np.amax(iat[self.RUidx:self.MIidx])*1.1)
		ax.plot(xx[1:], iat, linestyle='None', marker='.')
		ax.axvline(x=self.RU, color='r', label='interval')
		ax.axvline(x=self.RU+self.MI, color='r')
		m = np.mean(iat[self.RUidx:self.MIidx])
		ax.axhline(y=m,color='g', label='tx/s %0.02f'%(1/m))
		ax.legend()

		ax = fig.add_subplot(224, title='Inter-arrival in interval', xlabel='inter-arrival (s)')
		ax.hist(iat[self.RUidx:self.MIidx], 30)

		plt.show()

	def dump(self):
		print(self.name, self.throughput(), self.responseTime(), self.abortRate())

class TPCWRun(Run):
	def __init__(self, name):
		self.name=name
		self.start_times=[]
		self.end_times=[]
		self.abort_times=[]
		params={}

		stats = open(name)

		line = stats.readline()
		while line and line!='dat.starttimes = [\n':
			if line.startswith('%      -'):
				params[line[8:10]]=line[43:].strip()
			line = stats.readline()
		
		line = stats.readline()
		while line and line!='];\n':
			self.start_times.append(int(line))
			line = stats.readline()

		line = line and stats.readline()
		while line!='dat.endtimes = [\n':
			line = stats.readline()

		self.RUidx = -1
		self.MIidx = -1
		if 'RU' in params and 'MI' in params:
			self.RU = int(params['RU'])
			self.MI = int(params['MI'])
		else:
			self.RU = 99999999
			self.MI = 0
		
		line = line and stats.readline()
		while line!='];\n':
			t = int(line)
			if t > self.RU*1000 and self.RUidx == -1:
				self.RUidx = len(self.end_times)
			if t > (self.RU+self.MI)*1000 and self.MIidx == -1:
				self.MIidx = len(self.end_times)
			self.end_times.append(t)
			line = stats.readline()

		if self.MIidx<0 or self.RUidx<0:
			self.bounds(.2, .8)

class TPCCRun(Run):
	def __init__(self, name, tx):
		self.name=name
		self.start_times=[]
		self.end_times=[]
		self.abort_times=[]

		stats = open(name)

		# discard header
		line = stats.readline()

		line = stats.readline()
		while line:
			if line.startswith("-"):
				break
			v = line.split(':')
			ty = v[6].split()[1]
			if tx==None or tx==ty:
				t = int(v[2])
				if v[4] == "commit":
					ts = int(v[0])
					self.start_times.append(ts)
					self.end_times.append(t)
				elif v[4] == "aborting":
					self.abort_times.append(t)

			line = stats.readline()

		self.bounds(.2, .8)

class Scalability(object):
	def __init__(self, runs):
		self.runs=runs

	def plot(self):
		responseTimes=[run.responseTime() for run in self.runs]
		throughputs=[run.throughput() for run in self.runs]
		fig = plt.figure()
		ax = fig.add_subplot(111, title='Scalability curve', xlabel='throughput (tx/s)', ylabel='response time (s)')
		plt.xlim(0, np.amax(throughputs)*1.1)
		plt.ylim(0, np.amax(responseTimes)*1.1)
		ax.plot(throughputs, responseTimes)
		plt.show()

	def dump(self):
		for r in self.runs:
			r.dump()


def usage(e):
	print( """
usage: showtpc.py -b/--bench=[c|w] [-t/--tx=name] [--plot/-p] datafiles ..."
	- select Escada TPC-C or PHARM/UW-Madison TPC-W with -b/--bench
	- (TPC-C only) data can be filtered by transaction type with -t/--tx
	- data can be plotted graphically with -p/--plot
    	- with one argument, get a detailed analysis of a single run
    	- with more than one argument, draw a summary curve of multiple runs
""")
	sys.exit(e)

def main(argv):
	try:
		opts, args = getopt.getopt(argv[1:], "hb:t:p", ["help", "bench=", "tx=", "plot"])
	except getopt.GetoptError as e:
		print(str(e))
		usage(1)

	tpc=None
	plot=False
	tx=None

	for o, a in opts:
		if o in ('-h', '--help'):
			usage(0)
		elif o in ('-b', '--bench'):
			tpc=a
		elif o in ('-t', '--tx'):
			tx=a
		elif o in ('-p', '--plot'):
			if canPlot:
				plot=True
			else:
				assert canPlot, "requires graphical interface"
				

	if tpc in ('c', 'tpcc', 'escada'):
		runs=[TPCCRun(f,tx) for f in args] 
	elif tpc in ('w', 'tpcw', 'pharm'):
		runs=[TPCWRun(f) for f in args]
	else:
		usage(1)

	if len(runs)==0:
		usage(1)

	if len(runs)==1:
		r=runs[0]
	else:
		r=Scalability(runs)

	print("name throughput(tx/s) response_time(s) abort_rate")
	r.dump()
	if plot:
		r.plot()

if __name__ == "__main__":
    main(sys.argv)
