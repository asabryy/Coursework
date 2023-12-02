#!/usr/bin/env python3

import sys
import itertools
from itertools import combinations
import re

def formatRel(string):
    pattern = r"(?:^|(?<=,))[^,]*"
    r = []
    if ";" in string:
        for i in string.split(";"):
            r.append(sorted(re.findall(pattern, i)))
        return r, list(itertools.chain.from_iterable(r)) 
    else:
        r.append(re.findall(pattern, string))
        return r, re.findall(pattern, string)

def formatFD(string):
    pattern = r"(?:^|(?<=,))[^,]*"
    r = []
    if ";" in string:
        for i in string.split(";"):
            fd = []
            temp = i.split("/")
            for f in temp:
                fd.append(f.split(","))
            r.append(fd)
        return r
    else:
        fd = []
        temp = string.split("/")
        for f in temp:
            fd.append(f.split(","))
        r.append(fd)
        return r 

 # Algorithm 3.7: Closure of a Set of Attributes.

def atrClos(atr, FD):
    done = False
    kAttributes = set(atr)
    closure = atr.copy()
    #print(atr)
    while not done:
        done = True
        for f in FD:
            left = f[0]
            right = f[1]
            #print(left,"->", right)
            if set(left).issubset(kAttributes) and not set(right).issubset(kAttributes):
                #print(right)
                kAttributes.update(right)
                closure += right
                done = False
    
    #print("The closure for ", atr, "is ",kAttributes)
    return closure

# every superkey satisfies the first condition of a key: it 
# functionally determines all other attributes of the relation. However, a superkey 
# need not satisfy the second condition: minimality.

def superKey(atr, FD):
    attributes = []
    superkeys = []
    allAtr = set(atr)
    for i in range(len(atr)+1):
        attributes.extend(combinations(atr, i))
    for sub in attributes:
        atr_closure = atrClos(list(sub), FD)
        #print("sub is ",sub, "atrclos is ", atr_closure, " allatr is ", allAtr  )
        if set(atr_closure) == allAtr or allAtr.issubset(set(atr_closure)):
            superkeys.append(sub) 
    
    #print("The Super keys are: ", superkeys)
    return superkeys

def projFD(atr, FD):
    #print("checking ", atr, " with ", FD)
    Sk = superKey(atr, FD)
    fdList = []
    for f in FD:
        #print(set(f[0]), f[0], atr, f)
        if set(f[0]).issubset(atr) and set(f[1]).issubset(atr) and tuple(f[0]) not in Sk:
            fdList.append(f)
            #print(fdList)
    
    return fdList

def bcnf(atr, FD, answer):
    #print("==============DECOMP================")
    #print("The Attributes are ", atr)
    #print("The Functional D. are: ", FD)
    #atrClos(a, f)
    if projFD(atr, FD) != []:
        violations = projFD(atr, FD)
        for v in violations:
            #print("===========================================")
            #print("...........................................")
            R1 = sorted(atrClos(v[0], FD))
            R2 = sorted([x for x in atr if x not in R1] + v[0])
            #print("Given FDs: ", FD)
            print("the violation is ", v[0], "and the decomp is: R1:" ,R1, "and R2: ", R2)
            answer.append(bcnf(R1, FD, answer) + bcnf(R2, FD, answer)) 
    else:
        #print("no violation on ", atr)
        res = []
        res.append(atr)
        res = sorted(res,  key=lambda x:x[0])
        print("My decomp: ",res)
        return res

def threeNF(atr, FD):
    #print("3NF")
    return False

def solver(atr, FD, NF, dec):
    r, a = formatRel(atr)
    d, _a = formatRel(dec)
    f = formatFD(FD)
    result = []
    print("dec: " ,d)
    if (NF == "B"):
        #print("**************************************** =", sorted(bcnf(a, f), key=lambda x:x[0]))
        bcnf(a, f, result)
        print(result)
        return d in result
    elif (NF == "3"):
        return threeNF(a, f)

def main():
    if len(sys.argv) < 5:
        print("usage:./run \"<attributes>\" \"<FD>\" \"<B/3>\" \"<decomposition>\" ")
        exit(0)
    else:
        atr = sys.argv[1]
        FD = sys.argv[2]
        NF = sys.argv[3]
        dec = sys.argv[4]
        print(solver(atr, FD, NF, dec))



if __name__ == "__main__":
    main()
