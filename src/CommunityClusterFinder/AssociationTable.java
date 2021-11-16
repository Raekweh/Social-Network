package CommunityClusterFinder;

/**
 * The AssociationTable class is used to create an association table
 * for a given NxN matrix and calculate a weights table.
 * @Author Raymond Li & Pulin Angurala
 */
public class AssociationTable {
    private double[][] associationTable;
    private double[][] weights;
    private int size;

    public AssociationTable(double[][] associationTable) {
        this.associationTable = associationTable;
        this.size = associationTable.length;
        makeWeightTable();
    }

    /**
     * The makeWeightTable method generates a weight table
     * for a given association table.
     */
    public void makeWeightTable(){
        weights = new double[size][size];

        for(int i = 0; i < associationTable.length; i++){
            for (int j = 0; j < associationTable.length; j++){
                weights[i][j] = Math.abs(Math.log(associationTable[i][j]));
            }
        }
    }

    public double[][] getAssociationTable() {
        return associationTable;
    }

    public double[][] getWeights() {
        return weights;
    }

    public int getSize() {
        return size;
    }

    public String toString(){
        String s = "Association Table\n";


        for (int i = 0; i < associationTable.length; i++){
            for (int j = 0; j < associationTable.length; j++){
                s += associationTable[i][j]+" ";
            }
            s+= "\n";
        }


        s += "\nWeights Table\n";


        if (weights != null) {
            for (int i = 0; i < weights.length; i++) {
                for (int j = 0; j < weights.length; j++) {
                    s += weights[i][j] + " ";
                }
                s += "\n";
            }
        }
        return s;
    }
}