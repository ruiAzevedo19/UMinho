--------------------------- MODULE playgroundtla ---------------------------

CONSTANTS Worker 

VARIABLES Prepared, Committed, Aborted

Init == 
    /\ Prepared  = {} 
    /\ Committed = {} 
    /\ Aborted   = {}


Finish(w) == 
    /\ w \notin Prepared  
    /\ w \notin Aborted 
    /\ Prepared' = Prepared \cup { w } 
    /\ UNCHANGED << Committed , Aborted >>
    \* /\ Committed' = Committed  -|
    \*                             |--> Same as UNCHANGED 
    \* /\ Aborted' = Aborted      -|


Abort(w) ==
    /\ w \notin Aborted 
    /\ w \in Prepared => Aborted # {}
    /\ Prepared' = Prepared \ { w }
    /\ Aborted' = Aborted \cup { w }
    /\ UNCHANGED << Committed >>
    
Commit(w) ==
    /\ w \in Prepared \ Committed 
    /\ Aborted = {}
    /\ Committed' = Committed \cup { w }
    /\ UNCHANGED << Prepared , Aborted >>

Next == \E w \in Worker : Finish(w) \/ Abort(w) \/ Commit(w)

Spec == Init /\ [][Next]_<< Prepared , Committed , Aborted >>

Consistency == [] (Committed = {} \/ Aborted = {})

Stability == \A w \in Worker :
                        /\ [](w \in Committed => [] (w \in Committed))
                        /\ [](w \in Aborted => [](w \in Aborted)) 

\*  [] : always
\*  <> : eventually
\* \E  : some
\* \A  : all 
\* UNCHANGED : keeps state unchanged
\* ~>  : [](a => <> b)

=============================================================================
\* Modification History
\* Last modified Thu Dec 17 09:53:41 WET 2020 by ruiazevedo
\* Created Thu Dec 17 09:21:20 WET 2020 by ruiazevedo
