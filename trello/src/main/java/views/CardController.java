package views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import myTrello.Card;
import myTrello.Component;
import myTrello.User;
import myTrello.BLabel;

public class CardController implements ControllerInterface
{
	MainController mc;
	GridPane parentGrid;
	HashSet<BLabel> blabels;
	ArrayList<Component> components;
	ArrayList<User> members;
	FlowPane selectedFlowPane;

    @FXML
    private FlowPane labelsFlowPane;

    @FXML
    private FlowPane componentsFlowPane;

    @FXML
    private FlowPane membersFlowPane;

    @FXML
    private Button addLabelButton;

    @FXML
    private Button addComponentButton;

    @FXML
    private Button addMemberButton;

    @FXML
    private Label cardNameLabel;

    @FXML
    void onClickAddComponent(ActionEvent event) 
    {
    	selectedFlowPane = componentsFlowPane;
    	mc.showInputPopupView("Enter the name of your new component!", this);
    }

    @FXML
    void onClickAddLabel(ActionEvent event) 
    {
    	selectedFlowPane = labelsFlowPane;
    	mc.showInputPopupView("Enter the name of your new label!", this);
    }

    @FXML
    void onClickAddMember(ActionEvent event) 
    {
    	selectedFlowPane = membersFlowPane;
    	mc.showInputPopupView("Enter the name of your new card member!", this);
    }
    
    public void receiveString(String message)
	{
    	if (selectedFlowPane == componentsFlowPane)
    	{
    		Component newComponent = new Component(message, 5); // max capacity... what? 0_o
    		addComponent(newComponent);
    		selectedFlowPane = null;
    	}
    	else if (selectedFlowPane == labelsFlowPane)
    	{
    		BLabel newBLabel = new BLabel(message);
    		addBLabel(newBLabel);
    	}
    	else if (selectedFlowPane == membersFlowPane)
    	{
    		ArrayList<User> users = mc.getAllUsers(); // TODO: yo
			for (User user : users)
			{
				if (user.getName().equals(message))
				{
					addMember(user);
				}
			}
    	}
	}
    
    public void addBLabel(BLabel blabel)
    {
    	Label newLabel = new Label(blabel.getText());
		labelsFlowPane.getChildren().add(newLabel);
    }
    
    public void addComponent(Component component)
    {
    	Label newLabel = new Label(component.getDescription());
    	componentsFlowPane.getChildren().add(newLabel);
    }
    
    public void addMember(User user)
    {
    	Label newLabel = new Label(user.getName());
    	membersFlowPane.getChildren().add(newLabel);
    }
    
    public void setupScene(Card card, MainController mc, GridPane parentGrid)
    {
    	this.mc = mc;
    	this.parentGrid = parentGrid;
    	
    	cardNameLabel.setText(card.getName());
    	
    	blabels = card.getLabels().getMembers();
    	components = card.getComponents().getMembers();
    	members = card.getUsers().getMembers();
    	
    	for (BLabel blabel : blabels)
    	{
    		addBLabel(blabel);
    	}
    	
    	for (Component component : components)
    	{
    		addComponent(component);
    	}
    	
    	for (User member : members)
    	{
    		addMember(member);
    	}
    }
    
    private DataFormat accFormat;
    
//    // Drag and drop functionality, as provided by our group member Ian Cowan
// 	// in sprint 3, as per your instructions
//    
//    /**
//     * @param event - MouseEvent of the dragging
//     * 
//     * This works exactly the same as above, just for the accordion.
//     */
//    @FXML
//    public void Accordion_OnDragDetected(MouseEvent event)
//    {
//    	// Begin the dragging event
//    	TitledPane dragPane = (TitledPane) event.getSource();
//    	Dragboard db = dragPane.startDragAndDrop(TransferMode.ANY);
//		db.setDragView(dragPane.snapshot(null, null)); 
//		
//		// Add the pane format to the clipboard
//		ClipboardContent clip = new ClipboardContent();
//		Long randNum = (new Random()).nextLong();
//		String paneFormatName = "AccordionDragging" + String.valueOf(randNum);
//		accFormat = new DataFormat(paneFormatName);
//		clip.put(accFormat, " ");
//		db.setContent(clip);
//    }
//    
//    /**
//     * @param event - DragEvent of the dragging that is occuring
//     * @return the TitledPane that was dropped inside of. Returns null if none found
//     * 
//     * This again works exactly the same as above, except
//     * this time we are looking for children of an Accordion
//     */
//	private TitledPane getDroppedAccordion(DragEvent event)
//    {
//    	Node target = (Node) event.getTarget();
//    	
//    	while (! target.equals(parentGrid))
//    	{
//    		if (target.getParent().getClass().equals(Accordion.class))
//    		{
//    			return (TitledPane) target;
//    		}
//    		
//    		target = target.getParent();
//    	}
//    	
//    	return null;
//    }
//	
//	/**
//	 * @param event - DragEvent to complete
//	 */
//	private void completeDragAccordion(DragEvent event)
//	{
//		event.setDropCompleted(true);
//		accFormat = null;
//	}
//
//	/**
//	 * @param event - DragEvent that is dropped
//	 */
//    @FXML
//    public void Accordion_OnDragDropped(DragEvent event)
//    {
//    	// Get the dragboard, the dragPane, and the replacement
//        Dragboard db = event.getDragboard();
//        TitledPane replacement = getDroppedAccordion(event);
//        TitledPane dragPane = (TitledPane) event.getGestureSource();
//        
//        // Make sure the replacement and dragPane are not null and the
//        // drag was actually occuring
//        if (db.hasContent(accFormat) && replacement != null && dragPane != null)
//        {
//        	// Get the parent of both the dragPane and the replacement.
//        	// Remember since we allow switching between different cards,
//        	// they may not have the same parent
//        	Accordion parentReplacement = (Accordion) replacement.getParent();
//        	Accordion parentDragPane = (Accordion) dragPane.getParent();
//
//        	// Switching is going to be interesting here...
//        	// 
//        	int dragPaneIndex = parentDragPane.getPanes().indexOf(dragPane);
//        	int replacementIndex = parentReplacement.getPanes().indexOf(replacement);
//        	
//        	// If the parents are the same and the indices are the same of the
//        	// dragPane and the replacement, finish the drag because we dropped
//        	// the dragPane on top of itself
//        	if (parentReplacement.equals(parentDragPane) && dragPaneIndex == replacementIndex)
//        	{
//        		completeDragAccordion(event);
//        		return;
//        	}
//        	
//        	// Accordion doesn't allow duplicate entries, so we need temp values
//        	// in order to swap the panes. Let's create these temp TitledPanes,
//        	// but we don't need anythig inside of them since they're going to
//        	// exist for a split second...split second of fame...
//        	TitledPane temp1 = new TitledPane();
//        	TitledPane temp2 = new TitledPane();
//        	
//        	// Now, we get slightly complicated. Set the temps as placeholders for
//        	// the panes in the accordions. If we don't do it this way, we may lose
//        	// the spot in the index and if the index doesn't exist, we cannot use set
//        	parentDragPane.getPanes().set(dragPaneIndex, temp1);
//        	parentReplacement.getPanes().set(replacementIndex, temp2);
//        	
//        	// Now, we are going to swap the panes. So, we put the replacement in the
//        	// parent accordions and we place the replacement into the dragPane parent
//        	// at the dragPaneIndex, and the dragPane in the replacement parent at the
//        	// replacementIndex
//        	parentDragPane.getPanes().set(dragPaneIndex, replacement);
//        	parentReplacement.getPanes().set(replacementIndex, dragPane);
//        	
//        	// I just want to set temp1 and temp2 to null and suggest the garbage collector
//        	// to do some collecting in case there is a lot of swapping going on.
//        	// This is probably unnecessary and may not do anything, but memory is
//        	// a precious resource especially if this gets really big
//        	temp1 = null;
//        	temp2 = null;
//        	System.gc();
//            
//        	// Complete the drag
//            completeDragAccordion(event);
//        }
//        else
//        {
//        	// Complete the drag
//        	completeDragAccordion(event);
//        }
//    }
//
//    /**
//     * @param event - DragEvent that is occuring
//     * 
//     * Still not 100% sure why this is necessary, but again it breaks without it.
//     * This again works exactly the same as above
//     */
//    @FXML
//    public void Accordion_OnDragOver(DragEvent event)
//    {
//        Dragboard db = event.getDragboard();
//        
//        if (accFormat != null && db.hasContent(accFormat))
//            event.acceptTransferModes(TransferMode.ANY);
//    }
//
//
}
