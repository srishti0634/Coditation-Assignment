import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Game {
    //Grid containing states of all the cells
    int states[][];
    //A HashMap to store input : (like, (Key- Srishti, Value-(stateXIndex:3, stateYIndex:4))
    HashMap<String,Location> cells;
    //Row and Column size of the Grid
    int rowSize=5,colSize=5;

    //To initialize the hashmap and the grid with random values between 0 & 1
    void initialize(){
        states=new int[rowSize][colSize];
        cells=new HashMap<>();
        for(int i=0;i<rowSize;i++){
            for(int j=0;j<colSize;j++){
                states[i][j]=(int)Math.round(Math.random());
            }
        }
    }

    //To show the states of all the cells in a table format
    void showStatesOfAllCells(){
        for(int i=0;i<rowSize;i++){
            for(int j=0;j<colSize;j++){
                System.out.print(states[i][j]+"  ");
            }
            System.out.println();
        }
    }

    //To get the state of an existing name in the hashmap
    int queryByName(){
        try{
            Scanner sc=new Scanner(System.in);
            System.out.print("Enter The Name To Be Queried : ");
            String name=sc.nextLine();
            if(cells.containsKey(name)) {
                return states[cells.get(name).getXIndex()][cells.get(name).getYIndex()];
            }
        }
        catch(Exception e){
            System.out.println("!!!Some Error Occurred!!!");
        }
        //returning -1 for the name which donot exist in hashmap
        return -1;
    }

    void storeTheName(String name,int rowIndex,int colIndex){
        //initializing Location class when user wants a cell to be placed
        Location newName=new Location();
        newName.setXIndex(rowIndex);
        newName.setYIndex(colIndex);
        cells.put(name,newName);
        //setting the cell Live by updating its value to 1
        states[rowIndex][colIndex]=1;
    }

    //counting and returning the live neighbours of the cell with index i,j
    int neighboursOfCell(int i,int j,int r,int c){
        int count=0;
        if(i-1>=0 && j-1>=0 && i-1<r && j-1<c && states[i-1][j-1]==1)
            count++;
        if(i-1>=0 && j>=0 && i-1<r && j<c && states[i-1][j]==1)
            count++;
        if(i-1>=0 && j+1>=0 && i-1<r && j+1<c && states[i-1][j+1]==1)
            count++;
        if(i>=0 && j+1>=0 && i<r && j+1<c && states[i][j+1]==1)
            count++;
        if(i+1>=0 && j+1>=0 && i+1<r && j+1<c && states[i+1][j+1]==1)
            count++;
        if(i+1>=0 && j>=0 && i+1<r && j<c && states[i+1][j]==1)
            count++;
        if(i+1>=0 && j-1>=0 && i+1<r && j-1<c && states[i+1][j-1]==1)
            count++;
        if(i>=0 && j-1>=0 && i<r && j-1<c && states[i][j-1]==1)
            count++;
        return count;
    }

    void nextTick(){
        int nextState[][]=new int[rowSize][colSize];
        for(int i=0;i<rowSize;i++){
            for(int j=0;j<colSize;j++){
                int countOfLiveNeighbours=neighboursOfCell(i,j,rowSize,colSize);
                if(countOfLiveNeighbours<2||countOfLiveNeighbours>3)
                    nextState[i][j]=0;
                else if(countOfLiveNeighbours==3)
                    nextState[i][j]=1;
                else
                    nextState[i][j]=states[i][j];
            }
        }
        states=nextState;
    }

    void Tick(){
        try{
            Scanner sc=new Scanner(System.in);
            int noOfNewCells;
            System.out.print("Enter the Number Of New Cells To be Placed : ");
            noOfNewCells=Integer.parseInt(sc.nextLine());
            int positionXIndex,positionYIndex;
            String uniqueName;
            for(int i=0;i<noOfNewCells;i++){
                do {
                    System.out.print("Enter A Unique Name : ");
                    uniqueName=sc.nextLine();
                }while(cells.containsKey(uniqueName));
                do {
                    System.out.print("Enter The Position for X Coordinate : ");
                    positionXIndex=Integer.parseInt(sc.nextLine());
                }while(positionXIndex>=rowSize || positionXIndex<0);
                do {
                    System.out.print("Enter The Position for Y Coordinate : ");
                    positionYIndex=Integer.parseInt(sc.nextLine());
                }while(positionYIndex>=colSize || positionYIndex<0);
                storeTheName(uniqueName,positionXIndex,positionYIndex);
            }
            //this method is called to do the operations that the game does
            nextTick();
            System.out.print("Do You Want To See The New Generation's States ? Enter 1 For Yes and 0 For No : ");
            if(Integer.parseInt(sc.nextLine())==1){
                showStatesOfAllCells();
            }
        }
        catch(Exception e){
            System.out.println("!!!Some Error Occurred!!!");
        }
    }

    public static void main(String args[])throws IOException {
        int userChoice;
        Game game=new Game();
        while (true){
            Scanner sc=new Scanner(System.in);
            System.out.println("\n------------------------------------------");
            System.out.println("1. Start the Game");
            System.out.println("2. Go To Next Generation");
            System.out.println("3. Want to Query for a Particular State");
            System.out.println("4. Show the State's Grid");
            System.out.println("5. Exit The Game");
            System.out.print("Enter your choice : ");
            try {
                userChoice = Integer.parseInt(sc.nextLine());
            }
            catch(Exception e){
                System.out.println("Enter A Valid Choice");
                continue;
            }
            switch(userChoice){
                case 1:
                    game.initialize();
                    System.out.println("The Initial State Of All the Cells :");
                    game.showStatesOfAllCells();
                    break;
                case 2:
                    game.Tick();
                    break;
                case 3:
                    int stateOfName=game.queryByName();
                    if(stateOfName==-1)
                        System.out.println("The Name You Entered Is Not There In The Records");
                    else if(stateOfName==0)
                        System.out.println("The State Of The Cell Name Is : Dead");
                    else
                        System.out.println("The State Of The Cell Name Is : Live");
                    break;
                case 4:
                    game.showStatesOfAllCells();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Enter A Valid Number");
            }
        }
    }
}

class Location{
    private int stateXIndex;
    private int stateYIndex;
    void setXIndex(int x){
        stateXIndex=x;
    }
    void setYIndex(int y){
        stateYIndex=y;
    }
    int getXIndex(){
        return stateXIndex;
    }
    int getYIndex(){
        return stateYIndex;
    }
}
