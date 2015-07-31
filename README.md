#kedev-akka-tutorial

## Step1: Write a simple actor - The WordsCounter (scala: s1, java: j1)
* The actor system
  * Actor Hierarchy and paths
* Creating Actors
* Initialization Patterns
* Actor Lifecycle
* Actors API
* Basic Hooks
* 'tell' (vs. 'ask' explained later (Send-And-Receive-Future))
* Stopping Actors
* Testing Actors
* Logging in Actors
* Mailbox

## Step2: WordsCounter receives a String and count the words in it
* Parametrized Messages / Message Objects / need to be immutable
* More advanced message handling (receive)
* Counting words (or other thinks)

## Step3: What happens if a message is not received?
* How to simulate a lost message in test
* How to handle lost messages
    * tell again, timeouts, ask someone else for help, ...
    * akka persistence 

## Step4: WordsCounter should reads a file - FileWordsCounter
* What is the problem by reading a file
* What happens if actor breaks down (I/O Exception) -> Supervision

* Futures in akka
* Actor Monitoring
* Lifecycle Monitoring

## Step5: WordsCounter sends back how many times it was asked!
* Writer, DocWriter, TexWriter, HTMLWriter, ... (real world objects, scale it)

* Monitoring actors
* Monitoring actor lifecycle

## Step6: Send and receive messages form other actors
* Integration Testing

# To Teach:
## Actors and shared mutable state (The problem with State)
## Using the actors hierarchy 
## Actor Termination
## Location Transparency: Deploy Actors on different hosts
## Supervision and Monitoring
## Actor references, paths, addresses
## Message Delivery Reliability / Messaging garantees / Akka persistence
## Advanced Testing Methods
## Configuration
## Modelling Concurrency
## Common Patterns
## Mailboxes / Mailboxing
## Typed Actors
## FSM
## Routers?!


# Within Presentation

* Non-Blocking
* We are slower than we can be - or we parallelize
* The problems of parallelism
* Concurrency Theory
* Actor Models (Basic Idea)
  Carl Hewitt; Peter Bishop; Richard Steiger (1973). "A Universal Modular Actor Formalism for Artificial Intelligence". IJCAI.
  
 
 