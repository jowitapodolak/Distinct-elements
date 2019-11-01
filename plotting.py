#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Nov  1 09:04:43 2019

@author: jowi
"""
import numpy as np 

randomHashValues = np.linspace(1,1000000,1000000)

np.savetxt('randomNumbers.txt',randomHashValues)

