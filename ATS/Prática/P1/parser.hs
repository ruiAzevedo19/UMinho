module Parser where 

type Parser s r = [s] -> [(r,[s])]

-- parser a simple symbol
symbol :: Eq a => a -> Parser a a 
symbol _ [] = []
symbol s (x:xs) | s == x = [(s,xs)]
                | otherwise = []

-- parser symbol if satisfies the predicade
satisfy :: (s -> Bool) -> Parser s s
satisfy _ [] = []
satisfy p (x:xs) | p x = [(x,xs)]
                 | otherwise = []

-- parser a sequence of symbols
token :: Eq s => [s] -> Parser s [s]
token _ [] = []
token s r | take (length s) r == s = [(s, drop (length s) r)]
          | otherwise = []

-- empty production
succeed :: r -> Parser s r
succeed result inp = [(result, inp)]

-- or operator
(<|>) :: Parser s a -> Parser s a -> Parser s a
(p <|> q) inp = p inp ++ q inp

-- then operator, combines two parsers
(<*>) :: Parser s a -> Parser s b -> Parser s (a,b)
(p <*> q) inp = [((x,y),ys) | (x,xs) <- p inp , (y,ys) <- r xs]
