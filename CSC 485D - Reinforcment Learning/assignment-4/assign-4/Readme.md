# CSC485D Reinforcement Learning - Assignment 4 Dyna-SARSA

OpenAi gym environment implementation of CarMDP, RL-Based solutions for driving a car on slippery grid using Dyna framework

## Installation

unzip assign-4.zip and extract files

includes:

 - CarPilotEnv_A4.ipynb - jupyter notebook solution for Q1
 - plot.png - plot of average returns over different step values (0, 5, 10)
 - Readme.md - Readme file 

## Usage

in the working directory use in (terminal/cmd)

```Shell
user@domain:~$ jupyter notebook
```
## Jupyter Notebook
Press <kbd>Shift</kbd> + <kbd>Enter</kbd> for all cells respectively to run code

Or

select "run all" from the "Cell" dropdown menu

## RL Agent Parameters
4 additional parameter added to the ReinForcementLearningAgent constructor (__init__)
- discount
- epsilon
- alpha
- planningStep

all set to the default optimal values
```Python
class ReinforcementLearningAgent:
    """
    Your implementation of a reinforcement learning agent.
    Feel free to add additional methods and attributes.
    """
    def __init__(self, discount=0.9, epsilon=0.05, alpha=0.05, planningStep=5):
```

## Python Version (Student machine)

 - Python 3.8.12

## Refrences

 - "Deep Reinforcement Learning with Python" - Nimish Sanghi
  

