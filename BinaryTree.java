
public class BinaryTree {

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    public static class BinaryTrees {
        private Node root;
        private int count;

        public void add(int value) {
            Node node = new Node(value);
            count++;

            if (root == null) {       
                root = node;
                return;
            }

            Node current = root;
            while (true) {
                if (value < current.value) {
                    if (current.left == null) {
                        current.left = node;
                        return;
                    }
                    current = current.left;
                } else {
                    if (current.right == null) {
                        current.right = node;
                        return;
                    }
                    current = current.right;
                }
            }
        }

        public boolean contains(int value) {
            Node current = root;
            while (current != null) {
                if (value == current.value) {
                    return true;
                }
                current = value < current.value ? current.left : current.right;
            }
            return false;
        }

        public boolean remove(int value) {
            int before = count;
            root = remove(root, value);
            return count < before;
        }

        private Node remove(Node node, int value) {
            if (node == null) {
                return null;                         
            }
            if (value < node.value) {
                node.left = remove(node.left, value);
            } else if (value > node.value) {
                node.right = remove(node.right, value);
            } else {
                count--;
                if (node.left == null) {           
                    return node.right;
                }
                if (node.right == null) {         
                    return node.left;
                }
                Node successor = node.right;
                while (successor.left != null) {
                    successor = successor.left;
                }
                node.value = successor.value;
                node.right = removeMin(node.right); 
            }
            return node;
        }

        private Node removeMin(Node node) {
            if (node.left == null) {
                return node.right;                
            }
            node.left = removeMin(node.left);
            return node;
        }

        public int min() {
            ensureNotEmpty();
            Node current = root;
            while (current.left != null) {
                current = current.left;
            }
            return current.value;
        }

        public int max() {
            ensureNotEmpty();
            Node current = root;
            while (current.right != null) {
                current = current.right;
            }
            return current.value;
        }

        public int count() {
            return count;
        }

        public String printAll() {
            StringBuilder sb = new StringBuilder();
            inOrder(root, sb);
            return sb.length() == 0 ? "(дерево порожнє)" : sb.toString().trim();
        }

        private void inOrder(Node node, StringBuilder sb) {
            if (node == null) {
                return;
            }
            inOrder(node.left, sb);
            sb.append(node.value).append(' ');
            inOrder(node.right, sb);
        }

        private void ensureNotEmpty() {
            if (root == null) {
                throw new IllegalStateException("Дерево порожнє");
            }
        }
    }

    public static void main(String[] args) {
        BinaryTrees tree = new BinaryTrees();

        int[] values = {3, 1, 1, 9, 7, 10, 6, 8};
        for (int v : values) {
            tree.add(v);
        }

        System.out.println("Елементи: " + tree.printAll());
        System.out.println("Кількість: " + tree.count());
        System.out.println("Найменший: " + tree.min());
        System.out.println("Найбільший: " + tree.max());

        System.out.println("Чи є 7? " + tree.contains(7));
        System.out.println("Чи є 5? " + tree.contains(5));

        System.out.println("\nВидаляємо корінь 3 (два нащадки):");
        System.out.println("remove(3): " + tree.remove(3));
        System.out.println("Елементи: " + tree.printAll());

        System.out.println("\nВидаляємо 10 (лист):");
        System.out.println("remove(10): " + tree.remove(10));
        System.out.println("Елементи: " + tree.printAll());

        System.out.println("\nВидаляємо 9 (один нащадок):");
        System.out.println("remove(9): " + tree.remove(9));
        System.out.println("Елементи: " + tree.printAll());

        System.out.println("\nremove(100) (немає в дереві): " + tree.remove(100));
        System.out.println("Кількість: " + tree.count());
        System.out.println("Найменший: " + tree.min() + ", найбільший: " + tree.max());
    }
}
