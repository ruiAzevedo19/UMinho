module Problem1 where

-- 1. Graph structure: nodes and adjacency matrix (i.e. the edges) 
data Node = A | B | C | D | E | F  deriving (Show,Eq,Ord)

adj :: (Node,Node) -> Bool
adj p = case p of
  (A,B) -> True
  (A,C) -> True  
  (A,F) -> True 
  (B,A) -> True
  (B,C) -> True
  (B,E) -> True
  (B,F) -> True
  (C,A) -> True
  (C,B) -> True
  (C,D) -> True
  (D,C) -> True
  (D,E) -> True
  (E,B) -> True
  (E,D) -> True
  (E,F) -> True
  (F,A) -> True
  (F,B) -> True
  (F,E) -> True
  (_,_) -> False

type Path = [Node]

-- 2. Auxiliary functions
adjacentNodes :: Node -> [Node] -> [Node]
adjacentNodes n ns = filter (\x -> adj(n,x)) ns

allNodes :: [Node]
allNodes = [A,B,C,D,E,F]

choice :: ([a],[a]) -> [a]
choice = uncurry (++)

-- 3. Main body
{- For each node a in ns, if a is not already in p the function
   creates a new path by adding to the end of p the element a.
-}
addtoEnd :: Path -> [Node] -> [Path]
addtoEnd [] _ = []
addtoEnd _ [] = []
addtoEnd p ns = ns >>= (\n -> if not(elem n p) && adj(n,last p) then [choice(p,[n])] else [] )



-- Computes all Hamiltonian cycles starting from a given node
hCycles :: Node -> [Path]
hCycles n = do 
              p <-  return [n]
              p1 <- addtoEnd p  allNodes
              p2 <- addtoEnd p1 allNodes
              p3 <- addtoEnd p2 allNodes
              p4 <- addtoEnd p3 allNodes
              p5 <- addtoEnd p4 allNodes
              return p5 >>= (\p -> if (adj(last p,n)) then [choice(p,[n])] else [] )