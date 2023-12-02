package comp2402a4;

import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;

public class GeometricTree extends BinaryTree<GeometricTreeNode> {

    public GeometricTree() {
        super (new GeometricTreeNode());
    }

    public void inorderDraw() {
        assignLevels();
        GeometricTreeNode q = r;
        GeometricTreeNode s;
        s = q;
        int count = 0;

        while(q != null){
            if(s == q.right){
                s = q;
                q = q.parent;
            }
            else if(q.left != null && s!= q.left){
                s= q;
                q = q.left;
            }
            else if(q.right != null){
                q.position.x = count;
                count ++;
                s= q;
                q = q.right;
            }
            else{
                q.position.x = count;
                count ++;
                s= q;
                q = q.parent;
            }

        }
    }


    protected void randomX(GeometricTreeNode q, Random r) {
        if (q == null) return;
        q.position.x = r.nextInt(60);
        randomX(q.left, r);
        randomX(q.right, r);
    }


  
    public void leftistDraw() {
        assignLevels();
        GeometricTreeNode t = r;
        t.position.x = -1;

        Queue<GeometricTreeNode> v = new LinkedList<GeometricTreeNode>();
        v.add(r);

        while(!v.isEmpty()){
            GeometricTreeNode q = v.remove();

            if(t.position.y == q.position.y){
                q.position.x = t.position.x + 1;
                t = q;
            }

            else{
                q.position.x = 0;
                t = q;
            }

            if(q.left != nil){
                v.add(q.left);
            }

            if(q.right != nil){
                v.add(q.right);
            }
        }
    }


    public void balancedDraw() {
        calculateSizes(r);
        int x = 0;
        int y = 0;
        GeometricTreeNode current = firstNode();
        while(current != nil){
            current.size = currentSize(current);
            current = nextNode2(current);
        }
        current = r;
        do{
            while(current.left != nil || current.right != nil){
                if(current.left != nil && current.right != nil){
                    current = smallerChild(current);
                    y++;
                }
                else{
                    if(current.left != nil){
                        current = current.left;
                    }
                    else{
                        current = current.right;
                    }
                    x++;
                }
                setPosition(current, x, y);
            }
            do{
                current = current.parent;
            }while(current != nil && ((current.left == nil || current.right == nil) || largerChild(current).position.x > 0));
            if(current == nil){
                break;
            }
            y = current.position.y;
            current = largerChild(current);
            current.position.y = y;
            x++;
            current.position.x = x;
        }while(true);
    }

    private void calculateSizes(GeometricTreeNode q){
        if(q == null){
            return;
        }
        q.position.x = 0;
        calculateSizes(q.left);
        calculateSizes(q.right);
    }

    private void setPosition(GeometricTreeNode q, int x, int y){
        q.position.x = x;
        q.position.y = y;
    }

    private int currentSize(GeometricTreeNode q){
        if(q.left != nil && q.right != nil){
            return(q.left.size + q.right.size + 1);
        }

        if(q.left != nil){
            return(q.left.size + 1);
        }

        else if(q.right != nil){
            return(q.right.size + 1);
        }

        return 1;
    }

    private GeometricTreeNode nextNode2(GeometricTreeNode q){
        if(q.parent != nil && q.parent.left == q){
            q = q.parent;
            if(q.right != nil){
                q = q.right;
                while(q.left != nil || q.right != nil){
                    while(q.left != nil){
                        q = q.left;
                    }
                    if(q.right != nil){
                        q = q.right;
                    }
                }
            }
        }
        else{
            q = q.parent;
        }
        return q;
    }

    private GeometricTreeNode smallerChild(GeometricTreeNode q){
        if(q.left.size < q.right.size){
            return q.left;
        }
        else{
            return q.right;
        }
    }

    private GeometricTreeNode largerChild(GeometricTreeNode q){
        if(q.left.size < q.right.size){
            return q.right;
        }
        else{
            return q.left;
        }
    }

    protected void assignLevels() {
        assignLevels(r, 0);
    }

    protected void assignLevels(GeometricTreeNode q, int i) {
        if (q == null) return;
        q.position.y = i;
        assignLevels(q.left, i+1);
        assignLevels(q.right, i+1);
    }

    public static void main(String[] args) {
        GeometricTree m = new GeometricTree();
        galtonWatsonTree(m, 100);
        System.out.println(m);
        m.inorderDraw();
        System.out.println(m);
    }

}