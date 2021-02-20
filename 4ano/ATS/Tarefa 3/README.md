# Geração de ficheiros de logs
Nesta pasta é apresentado o código desenvolvido em *Haskell* para gerar ficheiros de *log* automaticamente, bem como o ficheiro de *log* gerado através desta ferramenta.   

Quando se corre o *script* de criação de *logs* é possível especficar o número de instâncias desejadas de cada tipo de dados. 

```sh
$ ghci Generator.hs
    *Generator> main
    Indique o número de Logs
    1
    Indique o número de Utilizadores
    10
    Indique o número de Voluntários
    10
    Indique o número de Transportadoras
    30
    Indique o número de Lojas
    45
    Indique o número de Encomendas
    150
    Indique o número de Produtos
    200
    Indique o número de Encomendas Aceites
    15
    Log files successfully created !
    *Generator>
```
