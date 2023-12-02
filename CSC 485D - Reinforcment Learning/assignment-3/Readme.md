# CSC485D Reinforcement Learning - Assignment 3 On-Policy SARSA

OpenAi gym environment implementation of CarMDP, RL-Based solutions for driving a car on slippery grid using On-Policy

## Installation

unzip assign-3.zip and extract files

includes:

 - CarPilotEnv-A3.ipynb - jupyter notebook solution for Q1 a and c
 - fig_1_plot.png - plot of average returns over different parameter values
 - fig_2_gamma_plot.png - plot of average return over different gamma values using optimal epsilon and alpha
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

## RL Agent Paramaters
3 additional parameter added to the ReinForcementLearningAgent constructor (__init__)
- discount
- epsilon
- alpha

all set to the default optimal values
```Python
class ReinforcementLearningAgent:
    """
    Your implementation of a reinforcement learning agent.
    Feel free to add additional methods and attributes.
    """
    def __init__(self, discount=0.9, epsilon=0.01, alpha=0.25):
```

## Python Version (Student machine)

 - Python 3.8.12

## Refrences

 - "Deep Reinforcement Learning with Python" - Nimish Sanghi
  

