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
To avoid thinking up exam results every time we want to create an instance of `ExamAttendance`,
we instead call the static method `getRandomAttendance` that creates random objects of this class.

## A closer look at blocks
[Course video in German](https://www.youtube.com/watch?v=DVfkBAK8Rl4) (~14min)

First, let us make things easier and only consider a single block.
Think of it as one entry in the list of things we want to record.
```java
public class Block {
  private ExamAttendance examAttendance;
  private String hash;
  private String previousHash;
  private String timestamp;

  /* omitting parts of the code to keep snippet brief */
}
```
Wait, that is way more than just an exam attendance.
Well yes. Otherwise, we wouldn't need an additional class :)

One attribute is the `timestamp`, which is not a necessity but nice to have.
Not only can we later trace the creation date of the block, but it also helps with checking consistency,
e.g. a block later on in the chain could not have an earlier timestamp than any previous block.

What helps even more with consistency is storing the `previousHash`, i.e. linking to the previous block.
A `hash` is an (almost) unique identifier for a block, like a fingerprint.
It is created by putting all the data of the block into a blender.

![A blender starting to mix some liquid](https://media.giphy.com/media/3o6wrFg0YiMbZqkf2E/giphy.gif)

The result is a long row of zeroes and ones.
Here comes the trick: part of the data we put into the blender is the `previousHash`, meaning the hash of the predecessor block.
A block's resulting `hash` (identity) is therefore tied to its ancestor.
Like we humans carry parts of our ancestors in us.
What a beautiful thing.

## The blender
[Course video in German](https://www.youtube.com/watch?v=aD9CSmltrIU) (~17min)

Obviously it is not a real blender that creates the `hash`.
We rather rely on an ingenious cryptographic algorithm to produce a unique outcome.
There are several algorithms available.
In our project we will use one from the [SHA-3](https://en.wikipedia.org/wiki/SHA-3) family, namely SHA3-256.
We don't need to get into the details of the algorithm, that's something for your cryptography or security class.
Nevertheless, we should be aware of two of its features.
To follow along, you can try out some hashing [here](https://www.browserling.com/tools/all-hashes).
For example:
```
input:
University of Applied Sciences Fulda

SHA3-256 output (64 characters in hexadecimal):
18a399ab855ef479c77820ac4155f3954835bc914e3f378844beb5e15a5317f8
```

It is noticeable that, no matter what input we are hashing, the SHA3-256 will always output a string with a **fixed length of 64**.
This string consists of the characters `0-9` and `a-e`.
It is the hexadecimal representation of a sequence of zeroes and ones with the length of 256.
That's where the _256_ in SHA3-_256_ comes from.

```
SHA3-256 output (256 digits in binary):
1100010100011100110011010101110000101010111101111010001111001110001110111100000100000101011000100000101010101111100111001010101001000001101011011110010010001010011100011111100110111100010000100010010111110101101011110000101011010010100110001011111111000
```

The algorithm has another exciting property.
If we change the input just _slightly_, we will get a **completely different output**:
```
input:
University of Applied Science Fulda

SHA3-256 output (in hexadecimal):
0db1827db07b5a4a2e75adfeb720df2af6115b68d4053c6803a6ce61b838abe2
```

Do you spot the difference? Yes, I omitted the trailing `s` in `Sciences` and the hash looks nothing like before.
We will later see why these properties are important to us.
First, let's look at the code.
Don't worry if you don't fully get what is happening here.
It will be explained below.

```java
public class Utils {
  public static String getSha3256Hash(String originalString) {
    /* ... */
  }

  private static String bytesToHex(byte[] hash) {
    /* ... */
  }
}
```
So we have a `Utils` class here with two methods.
Programmers often create this kind of classes to have helper methods at hand, e.g. to convert something into something else.
The advantage of these `public static` methods is that we can call them from anywhere in our code.

- `getSha3256Hash` turns an input string into a SHA3-256 hash
- `bytesToHex` converts an array of `byte` values into a `String`

To be honest, the standard Java class `MessageDigest` does the heavy lifting for us.
All we need to know is, that we can call `getSha3256Hash` to get the `hash` of whatever we put as an argument.
Considering our previous example:
`Utils.getSha3256Hash("University of Applied Sciences Fulda")` will return `18a399ab855ef479c77820ac4155f3954835bc914e3f378844beb5e15a5317f8`.

## The ingredients
Now that we are equipped with a hash-puking method, we can start creating hashes.
To achieve the blockchain's traceability and integrity, we mix everything we want to secure into the input.
Back in our `Block` class we call our helper method in the method `calculateHash`:

```java
public class Block {
  /* omitting parts of the code to highlight only the discussed parts */

  public Block(ExamAttendance examAttendance, String previousHash) {
    /* ... */
    this.hash = calculateHash();
  }
  
  private String calculateHash() {
    return Utils.getSha3256Hash(examAttendance.toString() + previousHash + timestamp);
  }
}
```

## Connect the ~~dots~~ blocks
[Course video in German](https://www.youtube.com/watch?v=4ltNm4nwyr8) (~15min)

It's time to get the ball rolling by building our chain.
You might have guessed what that means.
We need another class: the `HorstlChain`.
Its main task is to manage blocks (add and read).
Two attributes help to achieve this:

```java
public class HorstlChain {
  private Block[] horstlChain;
  private int currentIndex = 0;
  /* ... */
}
```
The `horstlChain` array stores the blocks.
`currentIndex` records the position of the latest block within the array.

When we give birth to our blockchain by calling the constructor, we initialize the array with a length and put the first (genesis) block into the array:

```java
public class HorstlChain {
  /* ... */
  public HorstlChain(ExamAttendance examAttendance) {
    horstlChain = new Block[100];
    horstlChain[0] = new Block(examAttendance, "genesis");
  }
  /* ... */
}
```

> A caveat with this implementation is that the size of the blockchain is fixed (in this case to 100 elements).
> However, if we reach that limit we can always create a larger array to which we transfer the content of the smaller array.

Afterwards we can add further blocks with the `addBlock` method.
Here we retrieve the hash of the latest block to use it as the `previousHash`.

```java
public class HorstlChain {
  /* ... */
  private void addBlock(ExamAttendance examAttendance) {
    String previousHash = horstlChain[currentIndex].getHash();
    horstlChain[++currentIndex] = new Block(examAttendance, previousHash);
  }
  /* ... */
}
```

So far we have only done simple array manipulations.
No trace of the promises of blockchain technology: easy to verify but difficult to change.
Remember the hashes? Now it is their time to shine.

## Checking the integrity
![A wedding proposal but instead of a ring there is a "verify check" in the box](https://media.giphy.com/media/l1KdaGQrZ2rKkLd4c/giphy.gif)

[Course video in German](https://www.youtube.com/watch?v=xrVeP2JAXBc) (~15min)

As we don't want to trust blindly, we verify with `isValid()` that everything has been stored correctly.
This method iteratively grabs two adjacent blocks and runs checks on them.

```java
public class HorstlChain {
  /* ... */
  private boolean isValid() {
    Block currentBlock;
    Block previousBlock;

    int i = 1;
    while (i <= currentIndex) {
      currentBlock = horstlChain[i];
      previousBlock = horstlChain[i - 1];

      // stored hash does not equal calculated hash
      if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
        return false;
      }

      // stored previousHash does not equal actual previous hash
      if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
        return false;
      }

      i++;
    }
    
    return true;
  }
  /* ... */
}
```

There are several ways an ill-intentioned individual could tamper our chain. Let's consider these two:
1. **change the `ExamAttendance` object in a block without changing the block's `hash`**    
   It is highly unlikely that with a different `ExamAttendance` object the hash function produces the same `hash` as before the alteration.
   To check the integrity, we run the calculation of the block's `hash` again and look if the new result is identical to the already stored `hash`.
2. **completely replace a block with a different one**    
   In this case, the culprit would do well to include the `hash` of the predecessor as the `previousHash` in the new block.
   Regardless, the successor of the illegitimately added block still contains the `hash` of the original predecessor as its `previousHash`,
   so this is what we check for in the second part of `isValid()`.
   
We can imagine more checks, e.g. trace if all timestamps are in chronological order or if a block's timestamp is earlier than the exam date etc.
I leave that as an exercise to the reader.

## Proof of Work
### Theory
[Course video in German](https://www.youtube.com/watch?v=pQ3_S2tEUnw) (~12min)

In a public blockchain, the data that is used to calculate the `hash` is visible for everyone.
That's why a well-equipped hacker might still get away with fraudulently exchanging blocks.

We visualize the attack with a blockchain of 4 blocks:
```
__________   __________   __________   __________
|        |   |prev: ab|   |prev: ee|   |prev: xy|
|hash: ab|---|hash: ee|---|hash: xy|---|hash: jk|
|________|   |________|   |________|   |________|
```
The attacker changes the content of the second block, resulting in a new hash.
```
              changed
__________   __________   __________   __________
|        |   |prev: ab|   |prev: ee|   |prev: xy|
|hash: ab|---|hash: ff|---|hash: xy|---|hash: jk|
|________|   |________|   |________|   |________|
```
To not get detected, the attacker stores a new `previousHash` in the third block.
As the `previousHash` is part of the calculated `hash`, the `hash` of the third block needs to change, too.
```
                           changed
__________   __________   __________   __________
|        |   |prev: ab|   |prev: ff|   |prev: xy|
|hash: ab|---|hash: ff|---|hash: rp|---|hash: jk|
|________|   |________|   |________|   |________|
```
This procedure continues until the last block in the chain is reached.
```
                                        changed
__________   __________   __________   __________
|        |   |prev: ab|   |prev: ff|   |prev: rp|
|hash: ab|---|hash: ff|---|hash: rp|---|hash: zt|
|________|   |________|   |________|   |________|
```
If we examine our chain now, everything looks innocent, although the second block's content has changed.

So the attacker "simply" has to recalculate all the hashes that follow the manipulated block.
Depending on the chain's length and the position of the targeted block this might take a while,
but it is still somehow doable.
We need a mechanism that turns _somehow doable_ into _highly impractical_. Introducing: **Proof of Work**.

[Proof of Work](https://en.wikipedia.org/wiki/Proof_of_work) means it is easily recognizable that you have put effort into something.
Imagine, before attaching a new block to the blockchain you would need to solve a Sudoku puzzle.
For each block you need to spend some time.
If an attacker now tries to change one block and reassign all following blocks,
there would be a lot of puzzles to solve...

In reality, there are no Sudokus involved.
Instead, the hashes serve as a (not so entertaining) puzzle.
The rule: a hash must comply with a certain characteristic, otherwise it is treated as invalid.
Let's say all hashes need to start with six zeroes, e.g. `000000a44b24323cdd3`.
If our hash does not comply, we need to generate new hashes, until we find one that adheres to the rule.

> Wait a minute. Hashes are based on the input to the hash function.
> In order to generate a different hash we would have to change the input.

Exactly.
But we don't want to change the `timestamp` nor the `previousHash` nor the data we would like to store.
These are fixed.
Therefore, we add another element to the input.
We call it [nonce](https://en.wikipedia.org/wiki/Cryptographic_nonce).
We then modify this element until we find a matching hash.
The nonce could be a number that we increment by 1 with each hash calculation.

Finding a solution to this hash puzzle will take a considerable amount of time (depending on the rules for the hash),
because the only known way at the moment is by [brute force searching](https://en.wikipedia.org/wiki/Brute-force_search) for a solution,
i.e. trying out all the possibilities.
