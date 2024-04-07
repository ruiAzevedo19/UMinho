module Problem2 where

import DurationMonad
import Problem1
import Data.Maybe

{- 1. *Labelled* graph structure: nodes and labelled adjacency matrix
(i.e. the labelled edges of the graph)
-}

adjT :: (Node,Node) -> Maybe Int
adjT p = case p of
           (A,B) -> Just 2
           (A,C) -> Just 3  
           (A,F) -> Just 6 
           (B,A) -> Just 30
           (B,C) -> Just 0
           (B,E) -> Just 4
           (B,F) -> Just 3
           (C,A) -> Just 60
           (C,B) -> Just 3
           (C,D) -> Just 50
           (D,C) -> Just 2
           (D,E) -> Just 3
           (E,B) -> Just 1
           (E,D) -> Just 3
           (E,F) -> Just 2
           (F,A) -> Just 4
           (F,B) -> Just 5
           (F,E) -> Just 3
           (_,_) -> Nothing


-- 2. Auxiliary functions
{- Given a node n and a list of nodes ns the function returns the nodes
in ns that can be reached from n in one step together with the time
necessary to reach them.
-}
tadjacentNodes :: Node -> [Node] -> [Duration Node]
tadjacentNodes n ns = ns >>= (\n' -> if (adj(n,n')) then [ Duration (fromJust (adjT(n,n')), n')] else [] )


-- 3. Main body
{- For each node a in ns, if a is not already in p the function creates
   a new path (like in the previous problem) and computes its cost.
-}

taddToEnd :: Duration Path -> [Duration Node] -> [Duration Path]
taddToEnd p ns = ns >>= (\n -> if not (elem (getValue n) (getValue p)) && adj( getValue n,(last.getValue) p)  then  [ Duration (getDuration n + getDuration p, choice(getValue p ,[ getValue n ])) ] else [] )


hCyclesCost :: Node -> [Duration Path]
hCyclesCost n = do 
                  dp <- return (Duration (0,[n]))
                  p1 <- f dp
                  p2 <- f p1 
                  p3 <- f p2 
                  p4 <- f p3
                  p5 <- f p4 
                  return p5 >>=  (\p -> if adj((last.getValue) p, n) then [Duration ( getDuration p + (fromJust $ adjT( (last.getValue) p, n)) ,choice(getValue p,[n]))] else [] )
            where f durationP = taddToEnd durationP (tadjacentNodes (last(getValue durationP)) allNodes)

            
-- the main program                                    
tsp = minimum . hCyclesCost