file = cp1718t

pdf: $(file).tex
	pdflatex $(file).tex

bib: 
	bibtex $(file)

all: $(file).tex
	lhs2TeX $(file).lhs > $(file).tex
	pdflatex $(file).tex
	#bibtex $(file)
	#pdflatex $(file).tex
	#pdflatex $(file).tex

# Automatically compile what is necessary, as many times as needed.
.PHONY : rubber
rubber :
	@rubber --pdf -f $(file)

clean:
	rm -rf *.aux *.log *.bbl *.bak *.ptb *.blg *.out *.spl 

cleanall : clean
	rm $(file).pdf

