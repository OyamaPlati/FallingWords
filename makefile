JFLAGS = -g
JC = javac

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \ WordDictionary.java \ WordRecord.java \ Score.java \ WordPanel.java \ WordApp.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

