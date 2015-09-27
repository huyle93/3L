/**
 * Chapter 7: EventsPanel.java
 * Creates the panel to be placed inside the Lag3 window.
 * Used( with modifications ) in all programs later in this book.
 * Version 3 of 3
 *
 * 1/30/08: rdb
 *    Renamed (old name was BallApp) and added JFrame parameter to constructor
 *    Pass JFrame to BouncingBall
 */
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class EventsPanel extends JPanel 
  implements Animated, MouseListener, MouseMotionListener
{
  //------------------------ instance variables ---------------------
  private final int FRAME_INTERVAL = 100;  // 100 msec per frame
  
  private Container _parent;
  
  // animation/gui variables
  ArrayList<Animated>  _movers = null;    // objects that can move
  ArrayList<AShape>    _ashapes = null;   // objects that need to be displayed
  ArrayList<Draggable> _draggable = null; // Objects that can be dragged
  ArrayList<Draggable> _dragging = null;  // Objects currently being dragged
  
  //--------------------- EventsPanel -----------------------------
  public EventsPanel( Container parent ) 
  {
    super();
    setLayout( null );   //Very important method call!!
    
    _parent = parent;
    
    this.setBackground( Color.white );
    
    addMouseListener( this );
    addMouseMotionListener( this );
    
    // create an ArrayList containing all objects that need updating
    //  on each frame.
    _movers   = new ArrayList<Animated>();
    
    // create an ArrayList for all objects that need to have their
    //  display methods executed in paintComponent
    _ashapes  = new ArrayList<AShape>();
    
    //////////////////////////////////////////////////////////////////////
    // Step 6a: initialize the _draggables and _dragging instance variables
    //////////////////////////////////////////////////////////////////////
    
    _draggable = new ArrayList<Draggable>();
    _dragging = new ArrayList<Draggable>();
    
    
    
    // Create a movable and draggable ball using AMovableEllipse
    AMovableEllipse bball = new AMovableEllipse( Color.RED );
    bball.setContainer( this ); // tell AMovableEllipse its move bnds
    bball.setLocation( 75, 75 );
    bball.setSize( 60, 60 );
    bball.setMove( 5, 9 );
    _movers.add( bball );
    _ashapes.add( bball );
    
    ////////////////////////////////////////////////////////////////
    // Step 1: Create another bouncing ball. Make it BLUE. Put it 
    //         somewhere new, make it a different size,  
    //         and give it different setMove parameters
    ////////////////////////////////////////////////////////////////
    
    AMovableEllipse newball = new AMovableEllipse( Color.BLUE );
    newball.setContainer( this ); // tell AMovableEllipse its move bnds
    newball.setLocation( 75, 75 );
    newball.setSize( 40, 50 );
    newball.setMove( 10, 14 );
    _movers.add( newball );
    _ashapes.add( newball );
    
    
    ////////////////////////////////////////////////////////////////
    // Step 2g: Add JMovableEllipse. Make it BLACK. Put it somewhere 
    //         new, make it a different size than any thing else, 
    //         and give it different setMove parameters
    ////////////////////////////////////////////////////////////////
    
    JMovableEllipse blackball = new JMovableEllipse( Color.BLACK );
    //blackball.setContainer( this ); // tell AMovableEllipse its move bnds
    blackball.setLocation( 75, 75 );
    blackball.setSize( 80, 60 );
    blackball.setMove( 15, 9 );
    _movers.add( blackball );
    //_ashapes.add( blackball );
    this.add( blackball );
    
    
    ////////////////////////////////////////////////////////////////
    // Step 2h: Add another JMovableEllipse. Make it GREEN. Put it 
    //         somewhere new, make it a different size than anything
    //          else, and give it different setMove parameters
    ////////////////////////////////////////////////////////////////
    
    JMovableEllipse gball = new JMovableEllipse( Color.GREEN );
    //blackball.setContainer( this ); // tell AMovableEllipse its move bnds
    gball.setLocation( 75, 75 );
    gball.setSize( 80, 60 );
    gball.setMove( 20, 18 );
    _movers.add( gball );
    //_ashapes.add( blackball );
    this.add( gball );
    
    
    ////////////////////////////////////////////////////////////////
    // Step 3d: Add a JPlayer. Make it CYAN. Put it somewhere new,
    //         and give it different setMove parameters
    ////////////////////////////////////////////////////////////////
    
    JPlayer jp = new JPlayer( Color.CYAN, 10, 10 );
    //blackball.setContainer( this ); // tell AMovableEllipse its move bnds
    //jp.setLocation( 75, 75 );
    jp.setSize( 80, 60 );
    jp.setMove( 15, 15 );
    _movers.add( jp );
    //_ashapes.add( blackball );
    this.add( jp );
    
    ////////////////////////////////////////////////////////////////
    // Step 5e: Add a non-moving JPlayer. Make it GRAY. Put it 
    //         somewhere new, and give it different setMove parms
    ////////////////////////////////////////////////////////////////
    
    JPlayer jpnm = new JPlayer( Color.GRAY, 10, 10 );
    //blackball.setContainer( this ); // tell AMovableEllipse its move bnds
    jpnm.setLocation( 305, 205 );
    jpnm.setSize( 80, 60 );
    //jp.setMove( 15, 15 );
    //_movers.add( jpnm );
    //_ashapes.add( blackball );
    this.add( jpnm );
    
    
    //------------------- 2 more starter objects -------------------
    AMovableEllipse ame = new AMovableEllipse( 100, 100 );
    ame.setColor( Color.MAGENTA );
    _ashapes.add( ame );
    _dragging.add( ame );
    
    AMovableRectangle amr = new AMovableRectangle( 120, 110 );
    amr.setSize( 30, 40 );
    amr.setColor( Color.ORANGE );
    _ashapes.add( amr );
    _dragging.add( amr );
    
    ///////////////////////////////////////////////////////////////
    // Step 5b. Add the above 2 objects to the _draggable array
    ///////////////////////////////////////////////////////////////
    
    _draggable.add( ame );
    _draggable.add( amr );
    
    
    
    // create and start up the FrameTimer
    FrameTimer timer = new FrameTimer( FRAME_INTERVAL, this );
    timer.start();
  }
  
  //++++++++++++++++++++++ Animated interface ++++++++++++++++++++++++
  private boolean      _animated; // not really used for the panel
  //---------------------- newFrame() --------------------------------
  public boolean isAnimated()
  {
    return _animated;
  }
  //---------------------- setAnimated( boolean ) --------------------
  public void setAnimated( boolean onOff )
  {
    _animated = onOff;
  }
  //---------------------- newFrame() -------------------------------
  public void newFrame() 
  {
    for ( int i = 0; i < _movers.size(); i++ )
      _movers.get( i ).newFrame();
    
    this.repaint();
  }
  
  //++++++++++++++++++ MouseListener methods +++++++++++++++++++++++++
  // You need to implement mousePressed and mouseDragged; 
  // the others must be there but will remain "empty".
  //
  private Point   _saveLoc;   // instance variable needed for dragging
  //------------------- mousePressed( MouseEvent ) -------------------
  /**
   * When completed, this method will take one of 2 actions:
   *   if mouse event location is inside one or more draggable objects,
   *      initiate dragging of those objects
   *   else
   *      generate a new BouncingBall object at the mouse location
   */
  public void mousePressed( MouseEvent me )
  {  
    Point loc = me.getPoint();  // get location of mouse press event
    int x = loc.x;
    int y = loc.y;
    
    ////////////////////////////////////////////////////////////////
    // Step 4c. is in the "else" clause of the "if" test below. Fill
    //       in the else before doing the "if" and "then" clauses
    //       -- that will happen in step 6
    ////////////////////////////////////////////////////////////////
    
        AMovableRectangle arec = new AMovableRectangle( Color.YELLOW );
        arec.setContainer( this ); 
        arec.setLocation( x, y );
        arec.setSize( 10 +( x + y ) % 31, 10 +( x + y ) % 31 );
        arec.setMove( 20, 20 );
        _movers.add( arec );
        _ashapes.add( arec );
    
    ////////////////////////////////////////////////////////////////
    // Step 6e. Add a test of the mouse position:
    //
    //    Invoke the findSelectedDraggers method; it takes ArrayList
    //       of Draggable objects and a Point that is mouse position.
    //       It returns list of Draggable objects that contain point 
    //    Both lists need to be instance, not local, variables.
    //    If returned list is not empty (its size > 0)
    //       _saveLoc = mousePosition (used in mouseDragged method)
    //    else
    //       execute the code you wrote in step 4c to add new balls.
    ////////////////////////////////////////////////////////////////
    
    
    _dragging = findSelectedDraggers( _draggable, loc );
    
    if ( _draggable.size() > 0 == true ) // step 6e, replace with the test of 
      //       findSelectedDraggers 
    {
      _saveLoc = loc;
    }
    else // create new a new bouncing box (AMovableRectangle)
    {
      ///////////////////////////////////////////////////////////
      // Step 4c.
      //  Add code to create a new YELLOW AMovableRectangle at  
      //     mouse event position, which is returned as a Point 
      //     by me.getPoint() and stored in the "loc" variable.
      //  The step increments for the ball's motion should be
      //            loc.x % 21 - 10, loc.y % 21 - 10
      //  The size (both width and height) should be determined 
      //    by the formula:
      //            10 + ( loc.x + loc.y ) % 26
      //  The color should be set to YELLOW.
      ///////////////////////////////////////////////////////////
      
//      AMovableRectangle arec = new AMovableRectangle( Color.YELLOW );
//      arec.setContainer( this ); 
//      arec.setLocation( x, y );
//      arec.setSize//( 10 +( x + y ) % 31, 10 +( x + y ) % 31 );
//      arec.setMove( 20, 20 );
//      _movers.add( arec );
//      _ashapes.add( arec );
      
    }
  }
  //------------- unused MouseListener methods -----------------
  public void mouseEntered( MouseEvent me ) {}
  public void mouseExited( MouseEvent me ) {}
  public void mouseMoved( MouseEvent me ) {}
  public void mouseReleased( MouseEvent me ){}
  //+++++++++++++++++++++ end MouseListener methods ++++++++++++++++++
  
  //-------------- findSelectedDraggers( ArrayList, Point ) ----------
  /**
   * First argument: ArrayList<Draggable>  objects that can be dragged
   * Second argument: Point mousePosition
   * 
   * this routine tests the mouse position against draggable W-objects
   * to see if the point is inside the bounds of the each object.
   * It returns an ArrayList of all the objects that enclose the
   * mouse position.
   */
  private ArrayList<Draggable> 
    findSelectedDraggers( ArrayList<Draggable> draggers, Point p )
  {
    ArrayList<Draggable> selected = new ArrayList<Draggable>();
    Iterator<Draggable> iter = draggers.iterator();
    while( iter.hasNext() )
    {
      Draggable d = iter.next();
      if ( d.isDraggable() && d.contains( p ))
        selected.add( d );
    }
    return selected;
  }
  
  //+++++++++++++++ MouseMotionListener methods ++++++++++++++++++++++
  //------------------- mouseDragged( MouseEvent ) -----------------  
  public void mouseDragged( MouseEvent me )
  { 
    
    ////////////////////////////////////////////////////////////////
    // Step 6f.
    // if _saveLoc is NOT null  
    //    1. get the mouse location; use me.getPoint()
    //    2. compute dx, dy as differences between this location 
    //       and _saveLoc
    //    3. set _saveLoc to the new location
    //    4. foreach entry in _dragging array
    //          call its moveBy method with dx, dy as arguments
    ////////////////////////////////////////////////////////////////
    
    if( _saveLoc!= null )
    {
      
      me.getPoint(); //1
      Point abc = getParent().getMousePosition();
      
      
      
      int dx;
      int dy;
      
      dx = abc.x - _saveLoc.x;
      dy = abc.y - _saveLoc.y;
      
      _saveLoc = abc;
      
      for( int i = 0; i < _dragging.size(); i++ )
      {
        _dragging.get( i ).moveBy( dx, dy );
      }
      
      
      System.out.println("inside the if");
      
    }
  }
  
//------------------- mouseClicked( MouseEvent ) -----------------  
  public void mouseClicked( MouseEvent me ){} 
//+++++++++++++++++++ end MouseMotionListener methods ++++++++++++++++++++
  
//--------------------- paintComponent( Graphics ) -----------------
  /**
   * Need this if we intermix A-objects with the J-objects
   *   need to explicitly draw the A-class objects
   */
  public void paintComponent( Graphics aBrush ) 
  {
    super.paintComponent( aBrush );
    Graphics2D brush2D = ( Graphics2D ) aBrush;
    
    Iterator<AShape> iter = _ashapes.iterator();
    while ( iter.hasNext() )
      iter.next().display( brush2D ); 
  }
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//------------------ main -------------------------------
  /**
   * Convenience main for testing the lab code.
   */
  public static void main( String [ ] args ) 
  {
    SwingEventsApp app = new SwingEventsApp( "SwingEventsApp" );
  }
}
