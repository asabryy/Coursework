# Implementation of B+-tree functionality.

from index import *
from node import *

# You should implement all of the static functions declared
# in the ImplementMe class and submit this (and only this!) file.
class ImplementMe:

    def isLeaf(node):
        if node.pointers.pointer[0] == 0 and node.pointers.pointer[1] == 0 and node.pointers.pointer[2] >= 0:
            return True
        return False
    
    def isFull(node):
        if -1 not in node.keys.keys:
            return True
        return False

    # Returns a B+-tree obtained by inserting a key into a pre-existing
    # B+-tree index if the key is not already there. If it already exists,
    # the return value is equivalent to the original, input tree.
    #
    # Complexity: Guaranteed to be asymptotically linear in the height of the tree
    # Because the tree is balanced, it is also asymptotically logarithmic in the
    # number of keys that already exist in the index.
    @staticmethod
    def InsertIntoIndex( index, key ):
        i=0
        if index.nodes == []:
            index = Index([Node()]*1)
            index.nodes[i].keys.keys = (key, -1) 
        elif key not in index.nodes[i].keys.keys and -1 in index.nodes[i].keys.keys:
            index.nodes[i].keys.keys = (index.nodes[i].keys.keys[0], key)
        else:
            return index
        return index



    # Returns a boolean that indicates whether a given key
    # is found among the leaves of a B+-tree index.
    #
    # Complexity: Guaranteed not to touch more no   des than the
    # height of the tree
    @staticmethod
    def LookupKeyInIndex( index, key ):
        root = index.nodes[0]
        i = 0
        while(root):
            if key in root.keys.keys:
                return True
            elif key<root.keys.keys[0]:
                i = (3*i)+1
            elif key>root.keys.keys[1] and root.keys.keys[1] != -1:
                i = (3*i)+3
            else:
                i = (3*i)+2

            if i in root.pointers.pointers:
                root = index.nodes[i]
            else:
                return False
        return False

    # Returns a list of keys in a B+-tree index within the half-open
    # interval [lower_bound, upper_bound)
    #
    # Complexity: Guaranteed not to touch more nodes than the height
    # of the tree and the number of leaves overlapping the interval.
    @staticmethod
    def RangeSearchInIndex( index, lower_bound, upper_bound ):
        key_list = []
        root = index.nodes[0]
        i = 0
        while(root):
            #print(i)
            if lower_bound in root.keys.keys:
                break
            elif lower_bound<root.keys.keys[0]:
                i = (3*i)+1
            elif lower_bound>root.keys.keys[1] and root.keys.keys[1] != -1:
                i = (3*i)+3
            else:
                i = (3*i)+2

            if i in root.pointers.pointers:
                root = index.nodes[i]
            elif root.keys.keys[0] <= upper_bound:
                break
            else:
                return list(key_list)
        
        end = True
        #print(i)
        while(end):
            for k in root.keys.keys:
                #print(k)
                if k < upper_bound and k >=lower_bound:
                    #print(k)
                    if k not in key_list:
                        key_list.append(k)
            if root.pointers.pointers == (0,0,0):
                break

            for p in range(1,3):
                if root.pointers.pointers[p] != 0:
                    root = index.nodes[root.pointers.pointers[p]]

        #print(list(key_list))
        return key_list

# def main():
#     btree = Index([Node()]*4)
#     btree.nodes[0] = Node(\
#             KeySet((42, 66)),\
#             PointerSet((1,2,3)))
#     btree.nodes[1] = Node(\
#             KeySet((7,-1)),\
#             PointerSet((0,0,2)))
#     btree.nodes[2]=Node(\
#             KeySet((42,-1)),\
#             PointerSet((0,0,3)))
#     btree.nodes[3]=Node(\
#             KeySet((66,87)),\
#             PointerSet((0,0,0)))
#     lower_bound = 7
#     upper_bound = 99

#     expected_output = [42,66,]

#     ImplementMe.RangeSearchInIndex( btree, lower_bound, upper_bound )
#     print(btree)

# if __name__ == '__main__':
#     main()    
