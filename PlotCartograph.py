# Python script to plot a matrix of numbers topographically

import matplotlib.pyplot as plt
import numpy as np
import sys

filename = sys.argv[1];

data = np.loadtxt(filename, delimiter='\t')

fig = plt.figure()

plt.imshow(data)

plt.show()


