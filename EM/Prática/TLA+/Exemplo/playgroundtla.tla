--------------------------- MODULE playgroundtla ---------------------------

EXTENDS Naturals

CONSTANTS N

Worker == 1 .. N

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
    /\ Worker \subseteq Prepared
    /\ Aborted = {}
    /\ Committed' = Committed \cup { w }
    /\ UNCHANGED << Prepared , Aborted >>

Next == \E w \in Worker : Finish(w) \/ Abort(w) \/ Commit(w)

state == << Prepared , Committed , Aborted >>

Spec == Init /\ [] [Next]_state /\ WF_state (\E w \in Worker : Commit(w))

Consistency == [] (Committed = {} \/ Aborted = {})

Stability == \A w \in Worker :
                        /\ [](w \in Committed => [] (w \in Committed))
                        /\ [](w \in Aborted => [](w \in Aborted)) 

Progress == [] (Committed # {} => <> (Worker \subseteq Committed))

\*  [] : always
\*  <> : eventually
\* \E  : some
\* \A  : all 
\* UNCHANGED : keeps state unchanged
\* ~>  : [](a => <> b)

=============================================================================
\* Modification History
\* Last modified Thu Dec 17 10:46:27 WET 2020 by ruiazevedo
\* Created Thu Dec 17 09:21:20 WET 2020 by ruiazevedo
