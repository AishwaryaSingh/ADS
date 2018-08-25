JCC = javac
FLAG = -g

default : BinaryHeap.class encoder.class decoder.class

BinaryHeap.class : BinaryHeap.java
	$(JCC) $(FLAG) BinaryHeap.java
encoder.class : encoder.java
	$(JCC) $(FLAG) encoder.java
decoder.class : decoder.java
	$(JCC) $(FLAG) decoder.java

clean:
	$(RM) *.class