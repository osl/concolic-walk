package rjc;

public class SimpleCounter {
  public int activeSubStates[] = new int [ 1 ];
  public int TopLevel_SimpleCounter_counter = 0;
  int execute_at_initialization_933 = 0;

  final static int SIMPLECOUNTER0 = 0x0;
  final static int COUNTERSTATE1 = 0x1;

  public void SimpleCounter_100000256_enter( int entryMode, int tpp )
  {
    if ( entryMode > -2 && entryMode <= 0 )
    {
      if ( (  ( 1 ) != 0  ) )
      {
        TopLevel_SimpleCounter_counter = (int)( 0 );
        CounterState_100000257_enter( 0, SIMPLECOUNTER0 );
      }

    }

  }
  public void SimpleCounter_100000256_exec(  )
  {
    if ( activeSubStates[ SIMPLECOUNTER0 & 0xFFFF ] == COUNTERSTATE1 )
    {
      CounterState_100000257_exec(  );
    }

  }
  public void SimpleCounter_100000256_exit(  )
  {
    if ( activeSubStates[ SIMPLECOUNTER0 & 0xFFFF ] == COUNTERSTATE1 )
    {
      CounterState_100000257_exit(  );
    }

  }
  public void CounterState_100000257_enter( int entryMode, int tpp )
  {
    if ( entryMode <= 0 )
    {
      activeSubStates[ SIMPLECOUNTER0 & 0xFFFF ] = COUNTERSTATE1;
    }

  }
  public void CounterState_100000257_exec(  )
  {
    if ( (  ( 1 ) != 0  ) )
    {
      ++TopLevel_SimpleCounter_counter;
      CounterState_100000257_exit(  );
      CounterState_100000257_enter( 0, COUNTERSTATE1 );
    }

  }
  public void CounterState_100000257_exit(  )
  {
    activeSubStates[ SIMPLECOUNTER0 & 0xFFFF ] = -COUNTERSTATE1;
  }
  public void Main( int[] counter_ )
  {
    if ( execute_at_initialization_933==1 )
    {
      SimpleCounter_100000256_exec(  );
    }

    execute_at_initialization_933 = 1;
    {
      counter_[ 0 ] = TopLevel_SimpleCounter_counter;
    }
  }
  public void Init(  )
  {
    int i = 0;

    i = 0;
    while( i < 1 )
    {
      activeSubStates[ (int)( i ) ] = 0;
      ++i;
    }

    TopLevel_SimpleCounter_counter = (int)( 0 );
    execute_at_initialization_933 = (int)( 0 );
    SimpleCounter_100000256_enter( -1, 0 );
  }
}
