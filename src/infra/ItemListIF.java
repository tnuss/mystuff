package infra;

import java.util.ArrayList;

public interface ItemListIF<DataType> {
    
    //ArrayList <Object> itemList = null;
    
    ArrayList <DataType> loadItemList() ;
    
    void setFileName (String str) throws Exception;
    void saveItemList(ArrayList <DataType> iList) throws Exception;
    void saveItemList() throws Exception;

}
