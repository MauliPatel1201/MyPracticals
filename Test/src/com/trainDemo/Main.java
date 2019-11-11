package com.trainDemo;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        Town  a = new Town("A");
        Town  b = new Town("B");
        Town  c = new Town("C");
        Town  d = new Town("D");
        Town  e = new Town("E");

        HashMap<Town, ArrayList<Destination>> graph = new HashMap<>();

        ArrayList<Destination> edgesA =  new ArrayList<Destination>();
        edgesA.add(new Destination("B",5,false));
        edgesA.add(new Destination("D",5,false));
        edgesA.add(new Destination("E",7,false));
        graph.put(a, edgesA);

        ArrayList<Destination> edgesB =  new ArrayList<>();
        edgesB.add(new Destination("C",4,false));
        graph.put(b, edgesB);

        ArrayList<Destination> edgesC =  new ArrayList<>();
        edgesC.add(new Destination("D",8,false));
        edgesC.add(new Destination("E",2,false));
        graph.put(c, edgesC);

        ArrayList<Destination> edgesD =  new ArrayList<>();
        edgesD.add(new Destination("C",8,false));
        edgesD.add(new Destination("E",6,false));
        graph.put(d, edgesD);

        ArrayList<Destination> edgesE =  new ArrayList<>();
        edgesE.add(new Destination("B",3,false));
        graph.put(e, edgesE);

        ArrayList<Town> t1 = new ArrayList<Town>();
        t1.add(a);
        t1.add(b);
        t1.add(c);
        System.out.println("Output 1 ==> A-B-C ==> " +  checkRouteCount(calculateDistance(t1,graph)));

        ArrayList<Town> t2 = new ArrayList<Town>();
        t2.add(a);
        t2.add(d);
        System.out.println("Output 2 ==> A-D ==> " +  checkRouteCount(calculateDistance(t2,graph)));

        ArrayList<Town> t3 = new ArrayList<Town>();
        t3.add(a);
        t3.add(d);
        t3.add(c);
        System.out.println("Output 3 ==> A-D-C ==> " +  checkRouteCount(calculateDistance(t3,graph)));

        ArrayList<Town> t4 = new ArrayList<Town>();
        t4.add(a);
        t4.add(e);
        t4.add(b);
        t4.add(c);
        t4.add(d);
        System.out.println("Output 4 ==> A-E-B-C-D ==> " +  checkRouteCount(calculateDistance(t4,graph)));

        ArrayList<Town> t5 = new ArrayList<Town>();
        t5.add(a);
        t5.add(e);
        t5.add(d);
        System.out.println("Output 5 ==> A-E-D ==> " + checkRouteCount(calculateDistance(t5,graph)));

        System.out.println("Output 6 ==>  C To C ==> " + checkRouteCount(findRoute(c,c,3, 0,graph)));

        resetGraph(graph);
        System.out.println("Output 7 ==>  A To C ==> " + checkRouteCount(findRoute(a,c,4, 0,graph)));

        resetGraph(graph);
        System.out.println("Output 8 ==>  A To C ==> " +checkRouteCount(checkShortDistance(a,c,0,0,graph)));

        resetGraph(graph);
        System.out.println("Output 9 ==>  B To B ==> " +checkRouteCount(checkShortDistance(b,b,0,0,graph)));

        resetGraph(graph);
        System.out.println("Output 10 ==>  C To C ==>" + checkRouteCount(findAllRoutesBetweenTowns(c,c,0,30,graph)));

    }

    static int findAllRoutesBetweenTowns(Town from, Town to, int distance, int maxDistance, HashMap<Town, ArrayList<Destination>> graph){
        int routes = 0;
        if (graph.containsKey(from) && graph.containsKey(to)) {

            ArrayList<Destination> getEdges = graph.get(from);
            int j = 0;

            while(getEdges != null && j <= getEdges.size() - 1) {
                distance += getEdges.get(j).distance;
                if(distance <= maxDistance) {
                    if(getEdges.get(j).toLocation.equals(to.toString())) {
                        routes++;
                        routes += findAllRoutesBetweenTowns(new Town(getEdges.get(j).toLocation), to, distance, maxDistance,graph);
                        j++;
                        continue;
                    }
                    else {
                        routes += findAllRoutesBetweenTowns(new Town(getEdges.get(j).toLocation), to, distance, maxDistance,graph);
                        distance -= getEdges.get(j).distance;
                    }
                }
                else {
                    distance -= getEdges.get(j).distance;
                }
                j++;
            }
        }
        else{
           return 0;
        }
        return routes;
    }


    static int checkShortDistance(Town from, Town to, int distance, int shortestPath,HashMap<Town,ArrayList<Destination>> graph) {
        if (graph.containsKey(from) && graph.containsKey(to)) {
            from.visited = true;
            ArrayList<Destination> getEdges = graph.get(from);
            int j = 0;

            while (getEdges != null && j <= getEdges.size() - 1) {
                if (getEdges.get(j).getToLocation().equals(to.toString()) || !getEdges.get(j).getVisited()) {
                    distance += getEdges.get(j).distance;
                }
                if (getEdges.get(j).toLocation.equals(to.toString())) {
                    if (shortestPath == 0 || distance < shortestPath)
                        shortestPath = distance;
                    from.visited = false;
                    return shortestPath;
                } else if (!getEdges.get(j).getVisited()) {
                    getEdges.get(j).setVisited(true);
                    shortestPath = checkShortDistance(new Town(getEdges.get(j).toLocation), to, distance, shortestPath, graph);
                    distance -= getEdges.get(j).distance;
                }
                j++;
                resetGraph(graph);
            }
        }
        from.visited = false;
        return shortestPath;
    }

    static int findRoute(Town from, Town to, int limit,int depth ,HashMap<Town,ArrayList<Destination>> graph) {
        int route = 0;
        if (graph.containsKey(from) && graph.containsKey(to)) {
            if(depth == limit){ return 0;}
            depth++;
            from.visited=true;
            ArrayList<Destination> getEdges = graph.get(from);

            int j=0;

            while(getEdges != null && j<=getEdges.size()-1) {
                if(getEdges.get(j).getToLocation().equals(to.toString())) {
                    route++;
                    j++;
                    continue;
                }
                else if(!getEdges.get(j).getVisited()) {
                    depth--;
                    getEdges.get(j).setVisited(true);
                    route += findRoute(new Town(getEdges.get(j).getToLocation()), to, limit,depth,graph);

                }
                j++;
            }

        }
        from.visited=false;
        return route;
    }

    static int calculateDistance(ArrayList<Town> town,HashMap<Town,ArrayList<Destination>> graph)
    {
        int distance= 0;
        for(int i=0; i<= town.size()-1; i++)
        {
            if(graph.containsKey(town.get(i)))
            {
                ArrayList<Destination> getEdges = graph.get(town.get(i));
                for(int j = 0 ; j <= getEdges.size()-1 ; j++)
                {
                    if(getEdges.get(j).getToLocation().equals(town.get(i+1).toString()))
                    {
                        distance+= getEdges.get(j).getDistance();
                        break;
                    }
                    else
                    {
                        distance=0;
                    }
                }
            }
            else{
                distance=0;
            }
            if( i+1 == town.size()-1)
            {
                break;
            }

        }
        return distance;
    }

    static String checkRouteCount(int route)
    {
        if(route == 0)
        {
            return "No Such Route";
        }
        else{
            return ""+route;
        }
    }

    private static void resetGraph(HashMap<Town, ArrayList<Destination>> graph) {

        ArrayList<Town> allTown= new ArrayList<>();
        allTown.add(new Town("A"));
        allTown.add(new Town("B"));
        allTown.add(new Town("C"));
        allTown.add(new Town("D"));
        allTown.add(new Town("E"));

        for (int i=0; i<=graph.size()-1;i++)
        {
            ArrayList<Destination> d= graph.get(allTown.get(i));
            for(int j=0; j<=d.size()-1;j++)
            {
                d.get(j).setVisited(false);
            }
        }
    }
}
