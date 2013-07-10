Process Sequence Files
======================

A Java command line tool to pre-proess the DNA Sequence in [FASTA format](http://en.wikipedia.org/wiki/FASTA_format).

Built with the following:
- [BioJava](http://www.biojava.org/)
- [Appache Common CLI](http://commons.apache.org/proper/commons-cli/)
- Java 6


The tool expects two input files, one FASTA file containing all DNA sequences and one Phred score file that contains all
the quality scores for the DNA sequences.

It will go through each DNA Sequence in the FASTA files and check if a barcode exists in the sequence.  (Barcode is a 10
character sequence that is defined in config/barcodes).  If there is a match, it will insert a marker to the beginning
of the sequence.  A max score (40) will be inserted into the beginning of corresponding Phred score sequence in the
Quality Score file as well.

It will then write the two modified files to the output files specified by the user input.

The tool can process a file set size around 1 GB in about 30 seconds.
