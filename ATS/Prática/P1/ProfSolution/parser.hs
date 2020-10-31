module Parser where 

infixl 2 <|>
infixl 3 <++>

type Parser s r = [s] -> [(r,[s])]

symbol_a :: Parser Char Char
symbol_a [] = []
symbol_a (x:xs) | x == 'a' = [('a',xs)]
                | otherwise = []

symbol :: Char -> Parser Char Char
symbol s [] = []
symbol s (x:xs) | s == x = [(s,xs)]
                | otherwise = []

satisfy p [] = []
satisfy p (x:xs) | p x = [(x,xs)]
                 | otherwise = [] 

token t [] = []
token t inp | t == take (length t) inp = [(t, drop(length t) inp)]
            | otherwise = []

succed r inp = [(r,inp)]

(<|>) :: Parser s a -> Parser s a -> Parser s a
(p <|> q) inp = p inp ++ q inp

(<+>) :: Parser s a -> Parser s b -> Parser s (a,b)
(p <+> q) inp = [ ((r,r'), ys)
                | (r, xs) <- p inp
                , (r',ys) <- q xs
                ]

(<++>) :: Parser s (a -> b) -> Parser s a -> Parser s b
(p <++> q) inp = [ (f r, ys)
                 | (f,xs) <- p inp
                 , (r,ys) <- q xs
                 ]

(<$$>) :: (a -> r) -> Parser s a -> Parser s r
(f <$$> p) inp = [ (f r, inp')
                 | (r, inp') <- p inp
                 ]

pSeq  =    f <$$> symbol 'a' <++> symbol ',' <++> symbol 'b'
     <|>   g <$$> symbol 'c'
     where f a1 a2 a3 = [a1,a3]
           g a1       = [a1]

-- spaces* (regexp)
spaces = f <$$> satisfy isSpace <++> spaces
      <|>       succed []
     where  f a b = a : b

zeroOrMore p =  f <$$> p <++> zeroOrMore p
            <|> succed []
            where f x xs = x:xs
oneOrMore p =  p <++> oneOrMore p
           <|> g <$$> p
           where f x xs =  xs: x:xs
                 g x    = [x]

token' t = f <$$> token t <++> spaces 
        where f a b = a

symbol' a = f <$$> symbol a <*> spaces 
         where f a b = a1

separatedBy p s =  f <$$> p
               <|> g <$$> p <++> s <++> separatedBy p s 
               where f a = [a]
               g a b c = a : c

enclosedBy a b f =  (\a b c -> b) <$$> a <++> b <++> f
                
