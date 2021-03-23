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

## The data we want to work with
[horstl](https://horstl.hs-fulda.de/) is the management system of Hochschule Fulda.
One feature of it is requesting one's grades.
We will create a blockchain that securely stores read-only grades of students and call it the horstlChain.
We instantiate objects of the class `ExamAttendance` as our data points.