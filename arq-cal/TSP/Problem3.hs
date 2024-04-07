module Problem3 where
import System.IO 
import Data.Char
import Data.List.Split
import System.Process 
import System.Random 

--  1. Graph structure:  
type Node = Char 
type Edge = (Node,Node)
type Edges = [Edge]


-- 2. A sequence of nodes its a path : 
type Path = [Node]


-- 3. Edge Colors :
type Colors = [String]
colors::Colors
colors = ["black","gray","blue","red","green","yellow","purple","brown"]


--5. AllNodes of a certain graph :
allNodes::[String]->[Node]
allNodes ns =  ns >>= (\n -> return $ head n )
 
--6. All edges of a certaing graph : 
listAdj::[String] -> Edges
listAdj [] = []
listAdj (h:t) = (origin,destiny) : listAdj t 
            where
                pair = filter isLetter (concat (splitOneOf "(,)" h))
                origin = head pair
                destiny = last pair

-- 6. Auxiliary functions
adjacentNodes :: Node -> Edges -> [Node]
adjacentNodes n edges = edges >>= (\edge -> if n==fst edge then return (snd edge) else [] )  


-- 7. 
choice :: ([a],[a]) -> [a]
choice = uncurry (++)

addtoEnd :: Path -> [Node] -> Edges -> [Path]
addtoEnd p ns edges = ns >>= (\n -> if not(elem n p) &&  (elem (last p) (adjacentNodes n edges)) then [choice(p,[n])] else [] )


-- Computes all Hamiltonian cycles starting from a given node
hCycles ::[Node] -> [Node] -> Edges->Int -> [Path]
hCycles n nodes edges counter = do
            newpath <- addtoEnd n nodes edges
            if counter /= 1 then hCycles newpath nodes edges (counter - 1)
                            else 
                                 if (elem (last newpath) (adjacentNodes (head newpath) edges)) then [choice(newpath,[head n] )] else [] 

strToEdges::String->String-> String
strToEdges _ [] =  []
strToEdges _ [x] = []
strToEdges color (origin:destiny:t) = [origin]++" -> "++[destiny]++"[style=solid, color="++color++"];\n"++ (strToEdges color (destiny:t)) 


randomColors :: [a] -> IO a  
randomColors l = do
    i <- randomRIO (0, length l - 1)
    return  $ (!!) l  i 



main :: IO ()
main = do 
    putStrLn "Insert the file name: "
    file <- getLine
    putStrLn "Insert the initial node: "
    node <- getLine 
    handle <- openFile file ReadMode
    contents <- hGetContents handle    
    let 
        graph =  splitAt 1 . words $ contents
        nodes =  allNodes.splitOn "," . head . fst $ graph
        edges = listAdj.snd $ graph
        size =  length nodes - 1 
        cycles = hCycles node nodes edges size

    print graph
    putStrLn "==============================NODES=============================="
    print nodes
    putStrLn "================================================================="
    putStrLn "==============================EDGES=============================="
    print edges
    putStrLn "================================================================="
    print cycles
    mapM_ write  cycles
    hClose handle



write :: Path -> IO()
write path = do 
    putStrLn ("Generating the :"++path++".pdf")
    color <- randomColors colors
    let 
        cycle = strToEdges color path
    appendFile ("./"++path++".dot") "digraph g {\n graph [pad=\"0.5\", nodesep=\"1\", ranksep=\"2\"] \nrankdir=LR\n"
    appendFile ("./"++path++".dot") cycle
    appendFile  ("./"++path++".dot") "}"
    system ("dot -Tpdf "++path++".dot > "++path++".pdf") >>= \exitCode -> print exitCode
    system ("open "++path++".pdf") >>= \exitCode -> print exitCode
    print "File Created  ..."