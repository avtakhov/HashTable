
package main;

import base.Pair;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.Iterator;

public class SplayTree<E, T> implements Searcher<E, T> {

    int size = 0;

    @Override
    public Iterator<Pair<E, T>> iterator() {
        if (root == null) {
            return new Iterator<Pair<E, T>>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public Pair<E, T> next() {
                    return null;
                }
            };
        }
        Node v = root;
        while (v.left != null) {
            v = v.left;
        }
        return new ProxyNode(v);
    }

    private Node find(Node node, Object key) {
        if (node == null) {
            return null;
        }
        int c = comparator.compare((E) key, node.key);
        if (c < 0 && node.left != null) {
            return find(node.left, key);
        } else if (c > 0 && node.right != null) {
            return find(node.right, key);
        }
        return splay(node);
    }

    @Override
    public void push(E key, T value) {
        if (root != null && comparator.compare((root = find(root, key)).key, key) == 0) {
            return;
        }
        size++;
        Pair<Node, Node> res = split(key);
        root = new Node(key, value);
        root.left = res.first;
        root.right = res.second;
        updateParent(root);
    }

    private final Comparator<E> comparator;
    private Node root;

    public SplayTree() {
        comparator = new Comparator<E>() {
            @Override
            public int compare(E e1, E e2) {
                Comparable<E> x = (Comparable<E>) e1;
                return x.compareTo(e2);
            }
        };
    }

    public SplayTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    private void setParent(Node child, Node parent) {
        if (child != null) {
            child.parent = parent;
        }
    }

    private void updateParent(Node node) {
        if (node != null) {
            setParent(node.left, node);
            setParent(node.right, node);
        }
    }

    private void rotate(Node node, Node parent) {
        Node gParent = parent.parent;
        if (gParent != null) {
            if (gParent.left == parent) {
                gParent.left = node;
            } else {
                gParent.right = node;
            }
        }

        if (parent.left == node) {
            parent.left = node.right;
            node.right = parent;
        } else {
            parent.right = node.left;
            node.left = parent;
        }
        updateParent(gParent);
        updateParent(node);
        updateParent(parent);
        node.parent = gParent;
    }

    private Node splay(Node node) {
        Node parent = node.parent;
        if (parent == null) {
            return node;
        }
        Node gParent = parent.parent;
        if (gParent == null) {
            rotate(node, parent);
            return node;
        }
        if ((gParent.left == parent) == (parent.left == node)) {
            rotate(parent, gParent);
            rotate(node, parent);
        } else {
            rotate(node, parent);
            rotate(node, gParent);
        }
        return splay(node);
    }

    @Override
    public boolean containsKey(Object key) {
        if (root == null) {
            return false;
        }
        root = find(root, key);
        return comparator.compare((E) key, root.key) == 0;
    }

    private Pair<Node, Node> split(E key) {
        if (root == null) {
            return new Pair<>(null, null);
        }
        root = find(root, key);
        int c = comparator.compare(key, root.key);
        if (c >= 0) {
            Node r = root.right;
            setParent(r, null);
            root.right = null;
            return new Pair<>(root, r);
        } else {
            Node l = root.left;
            setParent(l, null);
            root.left = null;
            return new Pair<>(l, root);
        }
    }

    private Node merge(Node t1, Node t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        t2 = find(t2, t1.key);
        t2.left = t1;
        t1.parent = t2;
        return t2;
    }

    @Override
    public T get(Object key) {
        if (root == null) {
            return null;
        }
        root = find(root, key);

        return root.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void erase(E key) {
        size--;
        root = find(root, key);
        setParent(root.left, null);
        setParent(root.right, null);
        root = merge(root.left, root.right);
    }

    class Node {
        public E key;
        public T value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(E key, T value) {
            this.key = key;
            this.value = value;
            left = right = parent = null;
        }
    }

    public class ProxyNode implements Iterator<Pair<E, T>> {
        private Node node;

        private ProxyNode(Node node) {
            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public Pair<E, T> next() {
            Pair<Node, Node> res = split(node.key);
            Pair<E, T> ans = new Pair(node.key, node.value);
            res.second = find(res.second, node.key);
            node = res.second;
            root = merge(res.first, res.second);
            return ans;
        }
    }

    private void print(Node node, PrintStream out) {
        if (node == null) {
            return;
        }
        print(node.left, out);
        out.print(node.key + " ");
        print(node.right, out);
    }

    public void print(PrintStream out) {
        out.print("[");
        print(root, out);
        out.println("]");
    }

}