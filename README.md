# FIT3077 Repository
# Santorini Game Project

## Project Overview
Santorini is a strategic 2-player board game where players take turns moving workers and building structures on a 5×5 grid. 
The goal is to be the first to move a worker onto the third level of a building. 
Special God cards add unique abilities, making each match dynamic and challenging.

## Project Structure
├──src
    ├── sprint3implementation
        ├──actions
            ├──Action
            ├──BuildAction
            ├──MoveAction
        ├──cards
            ├──Card
            ├──GodCard
            ├──Artemis
            ├──Demeter
            ├──Zeus
            ├──FunctionCard
            ├──SkipCard
            ├──SpecialAbility
        ├──characters
            ├──Player
            ├──Worker
        ├──grounds
            ├──Board
            ├──Tile
            ├──main
            ├──Application
            ├──GameController
        ├──setups
            ├──ResizeListener
            ├──Window
        ├──timers
            ├──PlayerTimer
            ├──TimerListener
        ├──towers
            ├──Dome
            ├──Tower
            ├──TowerLevel

every document can be found in `sprint3docs` package


# Before run the code
Make sure you set the resources file as `Resources Root`

Steps:
1. Right click on resources file
2. Navigate to `Mark Directory as`
3. click `Resources Root`


# How to run
Run the `Application` class under `main` package.