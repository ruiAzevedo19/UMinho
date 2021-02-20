from pyx import *
import numpy as stats
import sys

def main(args):
                types=["tx payment","tx payment 02", "tx neworder", "tx delivery","tx orderstatus","tx orderstatus 02","tx stocklevel"]
                outL=open("TPCC-latency-"+args[1]+".dat", 'w')
                outT=open("TPCC-throughput-"+args[1]+".dat", 'w')
                clients=[1]
                if len(clients)!=(len(args)-2):
                        print "The size of clients it not compatible with the list of files."
                        sys.exit(0)
                for i in range(2,len(args)):
                        fin=args[i]
                        throughput=0
                        mapLatency={}
                        print fin, clients[i-2]
                        for t in types:
                                mapLatency[t]=[]
                        mapLatency["total"]=[]
                        f = open(fin, 'r')
                        lines = f.readlines()
                        for line in lines:
                                sep=line[:-1].split(':')
                                if len(sep)>2 and sep[4]=="commit":
                                    mapLatency[sep[6]].append(float(sep[3]))
                                    mapLatency["total"].append(float(sep[3]))
                                elif sep[0]=="Measured tpmC":
                                    throughput=float(sep[1])
                                    print >>outT, clients[i-2], throughput
                        print >>outL, clients[i-2],
                        for t in types:
                                if len(mapLatency[t])>0:
                                    print >>outL,stats.mean(mapLatency[t]),
                                else :
                                    print >>outL,0,
                        print >>outL,   stats.mean(mapLatency["total"])
                        f.close()
                outL.close()
                outT.close()
main(sys.argv)
