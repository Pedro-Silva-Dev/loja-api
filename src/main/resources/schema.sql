create table if not exists usuarios
(
    id                        int auto_increment primary key,
    nome                      varchar(300)                         not null,
    telefone                  varchar(15)                          null,
    email                     varchar(150)                         not null,
    senha                     varchar(75)                          not null,
    url_foto                  varchar(300)                         null,
    token                     varchar(75)                          not null,
    ativo                     tinyint(1) default 1                 null,
    conta_nao_expirada        tinyint(1) default 1                 null,
    conta_nao_bloqueada       tinyint(1) default 1                 null,
    credenciais_nao_expiradas tinyint(1) default 1                 null,
    dhc                       timestamp  default CURRENT_TIMESTAMP null,
    dhu                       timestamp  default CURRENT_TIMESTAMP null
);

create table if not exists regras
(
    id        int auto_increment primary key,
    nome      varchar(75)                          not null,
    regra     varchar(75)                          not null,
    descricao varchar(500)                         not null,
    ativo     tinyint(1) default 1                 null,
    dhc       timestamp  default CURRENT_TIMESTAMP not null,
    dhu       timestamp  default CURRENT_TIMESTAMP not null
);

create table if not exists usuarios_regras
(
    id         int auto_increment primary key,
    usuario_id int not null,
    regra_id   int not null,
    constraint usuarios_regras_ibfk_1 foreign key (usuario_id) references usuarios (id),
    constraint usuarios_regras_ibfk_2 foreign key (regra_id) references regras (id)
);

create table if not exists categorias
(
    id         int auto_increment primary key,
    nome       varchar(300)                         not null,
    descricao  varchar(5000)                        null,
    ativo      tinyint(1) default 1                 not null,
    dhc        timestamp  default CURRENT_TIMESTAMP null,
    dhu        timestamp  default CURRENT_TIMESTAMP null
    );

create table if not exists promocoes
(
    id         int auto_increment primary key,
    nome       varchar(300)                         not null,
    descricao  varchar(5000)                        null,
    desconto   decimal(8, 2)                        not null,
    ativo      tinyint(1) default 1                 not null,
    dhi        timestamp                            null,
    dhf        timestamp                            null,
    dhc        timestamp  default CURRENT_TIMESTAMP null,
    dhu        timestamp  default CURRENT_TIMESTAMP null
    );

create table if not exists produtos
(
    id           int auto_increment primary key,
    nome         varchar(300)                         not null,
    descricao    varchar(5000)                        null,
    preco        decimal(8, 2)                        not null,
    estoque      int                                  null,
    ativo        tinyint(1) default 1                 not null,
    dhc          timestamp  default CURRENT_TIMESTAMP null,
    dhu          timestamp  default CURRENT_TIMESTAMP null,
    categoria_id int                                  null,
    constraint produtos_ibfk_1 foreign key (categoria_id) references categorias (id)
);

create table if not exists produtos_promocoes
(
    id         int auto_increment primary key,
    produto_id int not null,
    promocao_id   int not null,
    constraint produtos_promocoes_ibfk_1 foreign key (produto_id) references produtos (id),
    constraint produtos_promocoes_ibfk_2 foreign key (promocao_id) references promocoes (id)
)



