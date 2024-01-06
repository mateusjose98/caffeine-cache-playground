## Cache com Caffeine ![s](https://skills.thijs.gg/icons?i=java&theme=light)

![](https://miro.medium.com/v2/resize:fit:828/format:webp/1*vsdJw3bXanIsQQ5o0pGA1g.png)

POC com o uso de cache com o provedor Caffeine.
### Features:
 - Cache em leitura por id;
 - Cache após inserção de novo dado;
 - Multiplas regiões de cache;
 - Limitação de entradas por região;
 - TTL por região.

O projeto utiliza base de dados H2 (banco em memória não produtivo) com entrada inicial de 100 dados. É possível ler o cache atual via endpoint ``` GET caffeine/inspectCache ```