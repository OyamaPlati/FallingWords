JFLAGS = -g
JC = javac
JVM = java
MAIN = WordApp

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \ WordDictionary.java \ WordRecord.java \ Score.java \ UpdateThread.java \ CaughtThread.java \ WordPanel.java \ WordApp.java

default: classes

classes: $(CLASSES:.java=.class)

run: classes
	$(JVM) $(MAIN) ${ARGS}

clean:
	$(RM) *.class

