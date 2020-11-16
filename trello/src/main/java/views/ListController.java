package views;

import java.io.IOException;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import main.Main;
import myTrello.BList;
import myTrello.Card;

public class ListController implements ControllerInterface
{
	MainController mc;
	BList blist;
	GridPane parentGrid;

    @FXML
    private Accordion cardAccordion;

    @FXML
    private Button addNewCardButton;

    @FXML
    private Label listNameLabel;

    @FXML
    void onClickAddNewCard(ActionEvent event) 
    {
    	mc.showInputPopupView("Enter the name of your new card!", this);
    }
    
    public void addCardView(Card card)
    {
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/CardView.fxml"));
		try
		{
			TitledPane view = loader.load();
			CardController cont = loader.getController();
			cont.setupScene(card, mc, parentGrid);
			
	    	cardAccordion.getPanes().add(view);

		} catch (IOException e)
		{
			e.printStackTrace();
		}
    }
    
    public void setupScene(BList blist, MainController mc, BoardController bc)
    {
    	this.blist = blist;
    	this.mc = mc;
    	parentGrid = bc.listGridPane;
    	listNameLabel.setText(blist.getName());
    	// accordion.getPanes().add(pane1);
    }

	public void receiveString(String s)
	{
		Card newCard = new Card(s, blist);
		addCardView(newCard);
	}
	
	@FXML
    void TitledPane_OnDragDetected(MouseEvent event) {

    }
	
//	// Drag and drop functionality, as provided by our group member Ian Cowan
//	// in sprint 3, as per your instructions
//	
//	private DataFormat paneFormat;
//    
//    /**
//     * @param event - MouseEvent of the detected drag
//     * 
//     * This is activated when a drag is detected on a TitledPane
//     * (the encasing ones, TitledPane1, TitledPane2, TitledPane3)
//     */
//    
//    @FXML
//    public void TitledPane_OnDragDetected(MouseEvent event)
//    {
//    	// Begin the dragging event
//    	TitledPane dragPane = (TitledPane) event.getSource();
//    	Dragboard db = dragPane.startDragAndDrop(TransferMode.ANY);
//		db.setDragView(dragPane.snapshot(null, null)); 
//		
//		// Add the pane format to the clipboard
//		// We're using the clipboard for the actual dragging
//		ClipboardContent clip = new ClipboardContent();
//		Long randNum = (new Random()).nextLong();
//		String paneFormatName = "TitledPaneDragging" + String.valueOf(randNum);
//		paneFormat = new DataFormat(paneFormatName);
//		clip.put(paneFormat, " ");
//		
//		// Set the content of the dragboard to the clip that we created
//		db.setContent(clip);
//    }
//    
//    /**
//     * @param event - DragEvent of the drag on drop
//     * @return the TitledPane that it was dropped inside of. If none found, returns null
//     * 
//     * This handles finding the TitledPane that the dragging pane was dropped
//     * inside of. This is more complicated than you would think because there
//     * are automatically generated labels and other nodes inside of the TitledPane
//     * that we have to consider.
//     */
//	private TitledPane getDroppedTitledPane(DragEvent event)
//    {
//		// Get the event target
//    	Node target = (Node) event.getTarget();
//    	
//    	// If our target ever gets to the GridPane or null, then we know
//    	// that we haven't dropped inside of a TitledPane within the
//    	// GridPane
//    	//
//    	// This could also probably be done with recursion, but this is
//    	// how I sporadically wrote this method, so this is what I'm going with
//    	while (! target.equals(parentGrid))
//    	{
//    		// If the parent of the element is the GridPane, we know
//    		// that we have a TitledPane that we can cast from Node,
//    		// and also we have the TitledPane that we dropped inside of
//    		// so let's return it
//    		if (target.getParent().equals(parentGrid))
//    			return (TitledPane) target;
//    		
//    		// Get the next level up parent
//    		target = target.getParent();
//    	}
//    	
//    	// At this point we know that we don't have the TitledPane, so we just
//    	// return null
//    	return null;
//    }
//	
//	/**
//	 * @param event - DragEvent to complete
//	 */
//	private void completeDrag(DragEvent event)
//	{
//		// Set the drag event complete and nullify the paneFormat
//		event.setDropCompleted(true);
//		paneFormat = null;
//	}
//
//	/**
//	 * @param event - DragEvent that we are working with
//	 */
//    @FXML
//    public void GridPane_OnDragDropped(DragEvent event)
//    {
//    	// Get the dragboard and the replacement and dragging panes
//        Dragboard db = event.getDragboard();
//        TitledPane replacement = getDroppedTitledPane(event);
//        TitledPane dragPane = (TitledPane) event.getGestureSource();
//        
//        // Make sure the dragboard has the pane and make sure the replacement
//        // and dragPane are not null. If they're null, the drag & drop was invalid
//        // so we just end it and do nothing
//        if (db.hasContent(paneFormat) && replacement != null && dragPane != null)
//        {
//        	// Get the parent of the replacement (this is where we're going)
//        	GridPane parent = (GridPane) replacement.getParent();
//
//        	// Set the replacement and dragPane column indices to 0
//        	int colReplacement = 0;
//        	int colDragPane = 0;
//        	
//        	// For some reason, only if the index of the Pane is 0 in the GridPane,
//        	// it throws a NullPointerException, so if this happens, leave the index
//        	// as 0. If it works though, we get the new index
//        	try { colReplacement = GridPane.getColumnIndex(replacement); }
//        	catch (NullPointerException e) {}
//        	
//        	try { colDragPane = GridPane.getColumnIndex(dragPane); }
//        	catch (NullPointerException e) {}
//        	
//        	// If the column of the replacement and dragPane are equal, the dragPane
//        	// was dropped on top of itself, so we do nothing
//        	if (colReplacement == colDragPane)
//        	{
//        		completeDrag(event);
//        		return;
//        	}
//            
//        	// Now, we swap by removing both of the panes and then inserting them
//        	// into the appropriate spots
//            parent.getChildren().removeAll(dragPane, replacement);
//            parent.add(dragPane, colReplacement, 0);
//            parent.add(replacement, colDragPane, 0);
//            
//            // Complete the drag
//            completeDrag(event);
//        }
//        else
//        {
//        	// Complete the drag
//        	completeDrag(event);
//        }
//    }
//
//    /**
//     * @param event - DragEvent that is going on
//     * 
//     * I'm not 100% sure why this is necessary, but if we don't have it,
//     * the dragging all breaks.
//     */
//    @FXML
//    public void GridPane_OnDragOver(DragEvent event)
//    {
//    	// Get the dragboard
//        Dragboard db = event.getDragboard();
//        
//        // Confirm that the drag is still going on
//        if (paneFormat != null && db.hasContent(paneFormat))
//            event.acceptTransferModes(TransferMode.ANY);
//    }

}
