# Blockchain
## Introduction
This course is targeted towards computer science students of the [University of Applied Sciences in Fulda](https://www.hs-fulda.de/), but you can still follow along if you are familiar with Java.
There are accompanying videos in German: [YouTube Playlist](https://www.youtube.com/playlist?list=PLiisIVtqYuRuIy92gs0dpM-GginqDPvTM).
You can have a look at the slides used in the videos [here](https://jo3rn.github.io/blockchAIn/slides/).

If you do not speak German: the important parts will be covered here in this README or with alternative resources.

You might have learned the basics of Java in the module _Programming 1_ but are not sure how to apply them in a full-blown project.
Well, that's where _Programming 2_ comes in handy, but until then let's build a blockchain.
Needless to say that this will be a prototype and not the bullet-proof foundation of the next hot crypto-currency.
However, we will learn a thing or two about real-world blockchains along the way.

## So what is a blockchain?
![A dude with a red face and a hoodie saying "black magic"](https://media.giphy.com/media/KAuMz7XC91lsqeFgSt/giphy.gif)

Before we build, we shall know what to build.

[Course video in German](https://www.youtube.com/watch?v=6yd4sWluHck) (~9min)

Hearing about *blockchain* probably triggers [bitcoin](https://github.com/bitcoin/bitcoin) somewhere else in the brain.
Rightfully so, as it is a striking example of a blockchain's possibilities.
Instead of the course video you can also watch this to get an impression: [But how does bitcoin actually work?](https://www.youtube.com/watch?v=bBC-nXj3Ng4) (~27min)
or read [this paper](https://bitcoin.org/bitcoin.pdf) by the "founder" of bitcoin.

To put it briefly:
A blockchain is just a list of stuff you want to record.
Apart from the actual data you care about, each entry has some additional information to make it almost impossible to alter the entries later on without getting caught in the act.
Immutability is achieved by linking entries together (it is a _chain_ after all). _How?_ you might ask. Let's find out.

## The data we want to work with
[Course video in German](https://www.youtube.com/watch?v=NGfzF-nG_H0) (~12min)

[horstl](https://horstl.hs-fulda.de/) is the management system of Hochschule Fulda.
One feature of it is requesting one's grades.
We will create a blockchain that securely stores read-only grades of students and call it the horstlChain.
We instantiate objects of the class `ExamAttendance` as our data points.