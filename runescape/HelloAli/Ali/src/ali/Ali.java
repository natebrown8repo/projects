/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ali;

/**
 *
 * @author Nate
 */

import BasicIO.*;
import Media.*;
import java.util.*;
//import java.awt.*;

public class Ali {
  
  // img parse starting point
  public int gridx = 118;
  public int gridy = 8;
  
  public int tileSizeX = 32;
  public int tileSizeY = 32;
  
  public Picture pic;
  
  List<Picture> gridPictures = new ArrayList<Picture>();
  
  public Picture yellow1 = new Picture("images\\mind-1-yellow.png");
  public Picture orange1 = new Picture("images\\mind-1-orange.png");
  public Picture yellow2 = new Picture("images\\fire-2-yellow.png");
  public Picture orange2 = new Picture("images\\fire-2-orange.png");
  public Picture yellow3 = new Picture("images\\air-3-yellow.png");
  public Picture orange3 = new Picture("images\\air-3-orange.png");
  public Picture yellow4 = new Picture("images\\water-4-yellow.png");
  public Picture orange4 = new Picture("images\\water-4-orange.png");
  public Picture yellow5 = new Picture("images\\earth-5-yellow.png");
  public Picture orange5 = new Picture("images\\earth-5-orange.png");
  public Picture yellow6 = new Picture("images\\body-6-yellow.png");
  public Picture orange6 = new Picture("images\\body-6-orange.png");
  public Picture yellow7 = new Picture("images\\death-7-yellow.png");
  public Picture orange7 = new Picture("images\\death-7-orange.png");
  public Picture yellow8 = new Picture("images\\chaos-8-yellow.png");
  public Picture orange8 = new Picture("images\\chaos-8-orange.png");
  public Picture yellow9 = new Picture("images\\law-9-yellow.png");
  public Picture orange9 = new Picture("images\\law-9-orange.png");
  
  public List<Picture> selectedRunes = new ArrayList<Picture>();
  
  public Picture i1 = new Picture("images\\mind-1-selected.png");
  public Picture i2 = new Picture("images\\fire-2-selected.png");
  public Picture i3 = new Picture("images\\air-3-selected.png");
  public Picture i4 = new Picture("images\\water-4-selected.png");
  public Picture i5 = new Picture("images\\earth-5-selected.png");
  public Picture i6 = new Picture("images\\body-6-selected.png");
  public Picture i7 = new Picture("images\\death-7-selected.png");
  public Picture i8 = new Picture("images\\chaos-8-selected.png");
  public Picture i9 = new Picture("images\\law-9-selected.png");
  
  public Ali () {
    gridPictures.add(yellow1);
    gridPictures.add(orange1);
    gridPictures.add(yellow2);
    gridPictures.add(orange2);
    gridPictures.add(yellow3);
    gridPictures.add(orange3);
    gridPictures.add(yellow4);
    gridPictures.add(orange4);
    gridPictures.add(yellow5);
    gridPictures.add(orange5);
    gridPictures.add(yellow6);
    gridPictures.add(orange6);
    gridPictures.add(yellow7);
    gridPictures.add(orange7);
    gridPictures.add(yellow8);
    gridPictures.add(orange8);
    gridPictures.add(yellow9);
    gridPictures.add(orange9);
    
    selectedRunes.add(i1);
    selectedRunes.add(i2);
    selectedRunes.add(i3);
    selectedRunes.add(i4);
    selectedRunes.add(i5);
    selectedRunes.add(i6);
    selectedRunes.add(i7);
    selectedRunes.add(i8);
    selectedRunes.add(i9);
  }
  
  public int[][] imgToArray () {
    int pointerX = gridx;
    int pointerY = gridy;
    int[][] storage = new int[9][9];
    for (int i=0; i<9; i++){
      for (int j=0; j<9; j++){
        // left to right... top to bottom
        storage[i][j] = getValue(pointerX,pointerY);
        pointerX = pointerX + tileSizeX + 5;
        //break;
      }
      pointerX = gridx;
      pointerY = pointerY + tileSizeY + 5;
      //break;
    }
    return storage;
  }
  
  public int getValue(int x, int y) {
    // which picture is it?
    int b = -1;
    for (int a=0; a<gridPictures.size(); a++){
      int diff = 0;
      for (int i=0; i<tileSizeX; i++){
        for (int j=0; j<tileSizeY; j++){
          diff += Math.abs(pic.getPixel(x+i,y+j).getRed() - gridPictures.get(a).getPixel(i,j).getRed());
          diff += Math.abs(pic.getPixel(x+i,y+j).getBlue() - gridPictures.get(a).getPixel(i,j).getBlue());
          diff += Math.abs(pic.getPixel(x+i,y+j).getGreen() - gridPictures.get(a).getPixel(i,j).getGreen());
        }
      }
      //System.out.println(diff);
      if (diff == 0) b = a;
      if (diff == 0) break;
    }
    
    switch(b) {
      case 0: return 1;
      case 1: return 1;
      case 2: return 2;
      case 3: return 2;
      case 4: return 3;
      case 5: return 3;
      case 6: return 4;
      case 7: return 4;
      case 8: return 5;
      case 9: return 5;
      case 10: return 6;
      case 11: return 6;
      case 12: return 7;
      case 13: return 7;
      case 14: return 8;
      case 15: return 8;
      case 16: return 9;
      case 17: return 9;
    }
    
    return 0;
  }

  
  public int[][] matrixSubtract(int[][] a, int[][] b){
    int[][] retArray = new int[9][9];
    for (int i=0; i<a.length; i++){
      for (int j=0; j<a.length;j++){
        if (b[i][j] == 0) retArray[i][j] = a[i][j];
      }
    }
    return retArray;
  }
  
  public void doDisplay(int[][] matrix, BasicForm f){
    Picture p = f.getPicture("inputImg");
    for (int a=0; a<9; a++){
      int gridLocX = 118;
      int gridLocY = 8;
      for (int i=0; i<matrix.length; i++){
        for (int j=0; j<matrix.length; j++){

          if (matrix[i][j] == (a+1)) {
            // display the image
            Picture runePic = selectedRunes.get(a);
            for (int x=0; x<tileSizeX; x++){
              for (int y=0; y<tileSizeY; y++){
                
                Pixel tbc = p.getPixel(gridLocX+x, gridLocY+y);
                Pixel newPix = runePic.getPixel(x,y);
                tbc.setBlue(newPix.getBlue());
                tbc.setGreen(newPix.getGreen());
                tbc.setRed(newPix.getRed());
              }
            }
          }
          gridLocX = gridLocX + tileSizeX + 5;
        }
        gridLocX = 118;
        gridLocY = gridLocY + tileSizeY + 5;
      }
      if (a == 8) {
        f.accept("Close");
        f.close();
      }else{
        f.accept("Next");
      }
    }
  }
  
  
  /* 
   *    Sudoku stuff
   */
  public void writeMatrix(int[][] solution) {
        for (int i = 0; i < 9; ++i) {
            if (i % 3 == 0)
                System.out.println(" -----------------------");
            for (int j = 0; j < 9; ++j) {
                if (j % 3 == 0) System.out.print("| ");
                System.out.print(solution[i][j] == 0
                                 ? " "
                                 : Integer.toString(solution[i][j]));

                System.out.print(' ');
            }
            System.out.println("|");
        }
        System.out.println(" -----------------------");
    }
  
  public boolean solve(int i, int j, int[][] cells) {
        if (i == 9) {
            i = 0;
            if (++j == 9)
                return true;
        }
        if (cells[i][j] != 0)  // skip filled cells
            return solve(i+1,j,cells);

        for (int val = 1; val <= 9; ++val) {
            if (legal(i,j,val,cells)) {
                cells[i][j] = val;
                if (solve(i+1,j,cells))
                    return true;
            }
        }
        cells[i][j] = 0; // reset on backtrack
        return false;
    }
  
  public boolean legal(int i, int j, int val, int[][] cells) {
        for (int k = 0; k < 9; ++k)  // row
            if (val == cells[k][j])
                return false;

        for (int k = 0; k < 9; ++k) // col
            if (val == cells[i][k])
                return false;

        int boxRowOffset = (i / 3)*3;
        int boxColOffset = (j / 3)*3;
        for (int k = 0; k < 3; ++k) // box
            for (int m = 0; m < 3; ++m)
                if (val == cells[boxRowOffset+k][boxColOffset+m])
                    return false;

        return true; // good match
    }
  
  
  /* 
   *    Main stuff
   */
  public static void main(String [] args){
      
    Ali a = new Ali();
    
    System.out.println("Hello Ali!");
    
    // create an input form and get the image location
    String imgLoc;
    BasicForm inputForm = new BasicForm();
    inputForm.addTextField("imgInput", "Image Location");
    inputForm.accept();
    imgLoc = inputForm.readString("imgInput");
    System.out.println(imgLoc);
    inputForm.close();
    
    // create a form to show a next button and the image
    BasicForm form = new BasicForm();
    Picture p1 = new Picture(imgLoc);  
    a.pic = p1;          // populate the class picture variable so everything can see it
    form.addCanvas("inputImg", "Ali Image", p1.getWidth(), p1.getHeight());
    form.placePicture("inputImg", p1);
    form.accept("Calculate");
    
    // print the grid to console
    int[][] sudoku = a.imgToArray();
    a.writeMatrix(sudoku);
    int[][] before = a.imgToArray();
    if (a.solve(0,0,sudoku)){
      System.out.println("Its been solved!");
      a.writeMatrix(sudoku);
      // subract sudoku with before to see what is needed to display
      int[][] inverse = a.matrixSubtract(sudoku, before);
      a.writeMatrix(inverse);
      form.accept("Next");
      a.doDisplay(inverse, form);
      
      
    }else{
      System.out.println("Shit, it didnt work :(");
    }
    
  }
  
}