package com.panosso.calculate;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Graph {

    private Set<Node> nodes = new HashSet<>();
    
    public void addNode(Node node) {
        nodes.add(node);
    }

}