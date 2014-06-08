package org.goodfellas.model;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.lang.IllegalArgumentException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Graph {

	/* Map containing all the vertices, type <vertice id, vertice obj> */
	private Map<Integer, Vertice> vertices;

	private int numEdges;
	private int numVertices;
	
	private int origin;
	private int destination;

	public Graph() {

		vertices = new HashMap<Integer, Vertice>();
		numEdges = 0;
		numVertices = 0;

	}

	public Graph(Map<Integer, Vertice> vertices, int numEdges) {

		if (numEdges < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
		if (vertices == null) throw new IllegalArgumentException("Vertices must be non null");

		this.vertices = vertices;
		this.numVertices = vertices.size();
		this.numEdges = numEdges;

	}

	public void addVertice(Vertice vertice) {

		vertices.put( vertice.getId(), vertice );
		this.numVertices++;
		this.numEdges += vertice.getEdges().size();

	}

	@Override
	public String toString() {
	    String line = this.getNumVertices() + " vertices\n";
	    for(Integer key : vertices.keySet()) {
	        line += this.vertices.get(key).toString();
	    }
		return line;
	}

    public void toHtml(List<Edge> path) throws IOException {
        createHtmlGraph(path);
    }

    private void createHtmlGraph(List<Edge> caminho) throws IOException {
        String fileName = "graph";
        String fileExtension = ".json";

        File file = new File(fileName+fileExtension);
        FileWriter fileWriter = new FileWriter(fileName + fileExtension, false);

        PrintWriter printWriter = new PrintWriter(fileWriter);
        String printedVertices=
                "var graph = {"+"\n"+
                        "\"nodes\":["+"\n";
        String printedEdges=
                "],"+"\n"+
                        "\"links\":["+"\n";

        boolean [] v_path = new boolean[this.vertices.size()];//armazena se o vertice faz parte do menor caminho

        for(Edge g : caminho){
            v_path[g.getVerticeFrom().getId()] = true;
            v_path[g.getVerticeTo().getId()] = true;
        }

        /**
         * o for inclui ate o penultimo vertice no arquivo, que recebe virgula na estrutura
         * do .json.
         * o ultimo e feito na sequencia, sem a virgula
         */
        int i;
        for(i=0; i<this.vertices.size()-1;i++){
            Vertice vertice = this.vertices.get(i);
            String grupo;
            if(v_path[i]==true)
                grupo = "1";
            else
                grupo = "2";

            String vertexLabel = vertice.getId()+",["+vertice.getX()+","+vertice.getY()+"]";
            printedVertices += "{\"name\":\""+vertexLabel+"\",\"group\":"+grupo+"},"+"\n";

            int j=0;
            List<Edge> edges = vertice.getEdges();
            if(edges!=null && edges.size()>0){

                for(j=0; j<edges.size();j++ ){
                    Edge edge = edges.get(j);
                    String value;
                    if(containsEdge(edge, caminho))
                        value = "5";
                    else
                        value = "0.5";

                    String source = i+"";
                    String destination = edge.getVerticeTo().getId()+"";


                    //se for a ultima aresta e o proximo vertice existir e nao tiver arestas
                    if(j==edges.size()-1 && i+2==vertices.size()) {
                        if(vertices.get(i+1).getEdges()==null || vertices.get(i+1).getEdges().size()==0){
                            printedEdges += "{\"source\":"+source+",\"target\":"+destination+",\"value\":"+value+"}"+"\n";
                        }
                    } else {
                        printedEdges += "{\"source\":"+source+",\"target\":"+destination+",\"value\":"+value+"},"+"\n";

                    }
                }
            }

        }

        // last vertice
        Vertice vertice = this.vertices.get(new Integer(i));
        String grupo;
        if(v_path[i]==true)
            grupo = "1";
        else
            grupo = "2";

        String vertexLabel = vertice.getId()+",["+vertice.getX()+","+vertice.getY()+"]";
        printedVertices += "{\"name\":\""+vertexLabel+"\",\"group\":"+grupo+"}" +"\n";

        /**
         * arestas do ultimo vertice
         */
        int j=0;
        List<Edge> edges = vertice.getEdges();
        if(edges!=null && edges.size()>0){

            for(j=0; j<edges.size()-1;j++ ){
                Edge edge = edges.get(j);
                String value;
                if(containsEdge(edge, caminho))
                    value = "5";
                else
                    value = "0.5";

                String source = i+"";
                String destination = edge.getVerticeTo().getId()+"";

                printedEdges += "{\"source\":"+source+",\"target\":"+destination+",\"value\":"+value+"},"+"\n";
            }

            /*ultima aresta*/
            Edge edge = edges.get(j);
            String value;
            if(containsEdge(edge, caminho))
                value = "5";
            else
                value = "0.5";

            String source = i+"";
            String destination = edge.getVerticeTo().getId()+"";

            printedEdges += "{\"source\":"+source+",\"target\":"+destination+",\"value\":"+value+"}" +"\n";
        }

        // end of file
        printedEdges +=
                "]"+"\n"+
                        "}";

        printWriter.println(printedVertices);
        printWriter.println(printedEdges);

        fileWriter.close();
        printWriter.close();
    }

    boolean containsEdge(Edge e, List<Edge> path){
        for(Edge edge : path){
            if(edge.getVerticeTo().getId() == e.getVerticeTo().getId() &&
                    edge.getVerticeFrom().getId() == e.getVerticeFrom().getId()) {
                return true;
            }
        }
        return false;
    }

    public Map<Integer, Vertice> getVertices() {
		return vertices;
	}

	public void setVertices(Map<Integer, Vertice> vertices) {
		this.vertices = vertices;
	}

	public int getNumEdges() {
		return numEdges;
	}

	public void setNumEdges(int numEdges) {
		this.numEdges = numEdges;
	}

	public int getNumVertices() {
		return this.vertices.size();
	}

	public void setNumVertices(int numVertices) {
		this.numVertices = numVertices;
	}

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

}