#!/usr/bin/env python3

import torch
import torchvision
from torch.utils.data import DataLoader
from torchvision import datasets, transforms
from torchvision.transforms import ToTensor, Compose
import matplotlib.pyplot as plt
import numpy as np

def main():
    train_dataset = datasets.CIFAR10(root='data/', download=True, train=True, transform=ToTensor())
    test_dataset = datasets.CIFAR10(root='data/', download=True, train=False, transform=ToTensor())

    np.set_printoptions(threshold=np.inf)

    x_train = train_dataset.data
    y_train = np.array(train_dataset.targets)
    
    # print(type(x_train))
    # print(x_train.shape)
    # print(y_train.shape)
    # print(x_train[1])

    X_test = test_dataset.data
    y_test = np.array(test_dataset.targets)

    classes = train_dataset.classes
    print(classes)
    # print(y_test)

    figure = plt.figure(figsize=(8, 8))
    cols, rows = 3, 3
    for i in range(1, cols * rows + 1):
        sample_idx = torch.randint(len(train_dataset), size=(1,)).item()
        img, label = train_dataset[sample_idx]
        figure.add_subplot(rows, cols, i)
        plt.title(classes[label])
        plt.axis("off")
        plt.imshow(np.transpose(img, (1, 2, 0)))
    plt.show()
    


if __name__ == "__main__":
    main()