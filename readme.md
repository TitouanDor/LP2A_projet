# LP2A Project

## Installation
This project is writen in java. You need to have a list of card to play, a list is already given, to use it do ```cp card.dat.example card.dat```

## compile.bash
We had some problem with .class file whitch didn't want to compile again, so we create this file to delete all .class and compile all .java in a nice way
to use it you need first to have the right to execute the file ```chmod 777 compile.bash``` and then in the root directory of the project do ```./compile``` to compile and run the project

## Gamerule
We've made multiple gamerules, all are based from the official rules but some of them have difference :
- Skyjo : nothing added
- ShortSkyjo : just 1 round
- InvertSkyjo : you need to have the biggest score not the lowest 
- InvertShortSkyjo : you need to have the biggest score not the lowest in just 1 turn
