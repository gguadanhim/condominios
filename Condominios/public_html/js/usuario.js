/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var app = angular.module('usuarioApp',['ngRoute','ngResource']);

app.config(function($routeProvider){
    
    $routeProvider.when('/cadastro_usuario',{
        controller: 'CadastroUsuarioControler',
        templateUrl : 'templates/cadastro_usuario.html'
    }).when('/cadastro_usuario/:id/model.usuario/:id',{
        controller: 'CadastroUsuarioControler',
        templateUrl : 'templates/cadastro_usuario.html'
    }).when('/listagem_usuario',{
        controller: 'ListagemUsuarioControler',
        templateUrl : 'templates/listagem_usuario.html'
        
    }).when('/cadastro_produto',{
        controller: 'CadastroProdutoControler',
        templateUrl : 'templates/cadastro_produto.html'    
    }).when('/cadastro_produto/:id',{
        controller: 'CadastroProdutoControler',
        templateUrl : 'templates/cadastro_produto.html'
    }).when('/listagem_produto',{
        controller: 'ListagemProdutoControler',
        templateUrl : 'templates/listagem_produtos.html'
    
    }).when('/cadastro_cliente',{
        controller: 'CadastroClienteControler',
        templateUrl : 'templates/cadastro_cliente.html'    
    }).when('/cadastro_cliente/:id',{
        controller: 'CadastroClienteControler',
        templateUrl : 'templates/cadastro_cliente.html'
    }).when('/listagem_cliente',{
        controller: 'ListagemClienteControler',
        templateUrl : 'templates/listagem_cliente.html'    
    
    }).when('/cadastro_fornecedor',{
        controller: 'CadastroFornecedorControler',
        templateUrl : 'templates/cadastro_fornecedor.html'    
    }).when('/cadastro_fornecedor/:id',{
        controller: 'CadastroFornecedorControler',
        templateUrl : 'templates/cadastro_fornecedor.html'
    }).when('/listagem_fornecedor',{
        controller: 'ListagemFornecedorControler',
        templateUrl : 'templates/listagem_fornecedor.html'    
    
    }).when('/cadastro_Pedido',{
        controller: 'CadastroPedidoControler',
        templateUrl : 'templates/cadastro_Pedido.html'    
    }).when('/cadastro_Pedido/:id',{
        controller: 'CadastroPedidoControler',
        templateUrl : 'templates/cadastro_Pedido.html'
    }).when('/listagem_Pedido',{
        controller: 'ListagemPedidoControler',
        templateUrl : 'templates/listagem_Pedido.html'        
    
    }).when('/cadastro_Venda',{
        controller: 'CadastroVendaControler',
        templateUrl : 'templates/cadastro_Venda.html'    
    }).when('/cadastro_Venda/:id',{
        controller: 'CadastroVendaControler',
        templateUrl : 'templates/cadastro_Venda.html'
    }).when('/listagem_Venda',{
        controller: 'ListagemVendaControler',
        templateUrl : 'templates/listagem_Venda.html'
    
   
    }).when('/cadastro_empresa',{
        controller: 'CadastroempresaControler',
        templateUrl : 'templates/cadastro_empresa.html'    
    }).when('/cadastro_empresa/:id',{
        controller: 'CadastroempresaControler',
        templateUrl : 'templates/cadastro_empresa.html'
    }).when('/listagem_empresa',{
        controller: 'ListagemempresaControler',
        templateUrl : 'templates/listagem_empresa.html'
     
     
        
    }).otherwise('/listagem_usuario');
});

var empresa = {"cnpj":"1","endereco":"1","id":1,"nome":"1","telefone":"1"};
   
app.controller('ListagemUsuarioControler',function($scope,usuariosService){
    
    listarUsuarios();

    function listarUsuarios(){
        usuariosService.getUsuarios().then(function(usuarios){
            $scope.usuarios = usuarios;
            $scope.empresa = empresa;
        });
    };
    
    $scope.excluir = function(usuario) {
        usuariosService.excluir(usuario).then(function(){
            listarUsuarios();
        });
    };
});
app.controller('CadastroUsuarioControler',function($routeParams,$scope,$location,usuariosService){
    var id = $routeParams.id;
    $scope.empresa = empresa;
    
    if(id){
        usuariosService.getUsuario(id).then(function(usuarios){
            $scope.usuario = usuarios;
        });
    }else{
        $scope.usuario = {};
    }
    
    $scope.salvar = function(usuario) {
        usuariosService.salvarUsuario(usuario).then(function(){
            $location.path('listagem_usuario');
        });
        $scope.usuario = {};
    };

    $scope.cancelar = function(usuario) {
        $scope.usuario = {};
        $location.path('listagem_usuario');
    };
});
app.service('usuariosService',function(UsuariosResource) {
    this.getUsuario = function(id) {
        return UsuariosResource.BuscarUsuario({id:empresa.id},{id:id}).$promise;
    };

    this.getUsuarios = function() {
        return UsuariosResource.BuscarUsuarios({id : empresa.id}).$promise;
    };

    this.salvarUsuario = function(empresa,usuario) {
        if (usuario.id){            
            return UsuariosResource.update({id : usuario.id},usuario).$promise;
        }else{
            return UsuariosResource.adicionarUsuario({id : empresa.idempresa},usuario).$promise;
        }
    };

    this.excluir = function(usuario) {
        return UsuariosResource.delete({id : usuario.id}).$promise;
    };  
});
app.factory('UsuariosResource',function($resource){
         var Url = 'http://localhost:8084/condominio_backend/webresources/model.empresa/:id';
         return $resource(Url,{},{
                    update:{
                        method:'PUT'
                    },
                    adicionarUsuario:{
                        method:'POST',
                        url: Url + '/model.usuario',
                        params:{
                            id:'@id'
                        }
                    },
                    BuscarUsuarios:{
                        method:'GET',
                        isArray:true,
                        url: Url + '/model.usuario',
                        params:{
                            id:'@id'
                        }
                    },
                    BuscarUsuario:{
                        method:'GET',
                        isArray:false,
                        url: Url + '/model.usuario/:id',
                        params:{
                            id:'@id',
                            id:'@id'
                        }
                    }
                
    })
});


app.controller('ListagemProdutoControler',function($scope,ProdutoService){
    listarProdutos();

    function listarProdutos(){
        ProdutoService.getProdutos().then(function(produtos){
            $scope.produtos = produtos;
        });
    };
    
    $scope.excluir = function(produto) {
        ProdutoService.excluir(produto).then(function(){
            listarProdutos();
        });
    };
});
app.controller('CadastroProdutoControler',function($routeParams,$scope,$location,ProdutoService){
    var id = $routeParams.id;
    
    if(id){
        ProdutoService.getProduto(id).then(function(produtos){
            $scope.produto = produtos;
        });
    }else{
        $scope.produto = {};
    }
    
    $scope.salvar = function(produto) {
        ProdutoService.salvarProduto(produto).then(function(){
            $location.path('listagem_produto');
        });
        $scope.produto = {};
    };

    $scope.cancelar = function(produto) {
        $scope.produto = {};
        $location.path('listagem_produto');
    };
});
app.service('ProdutoService',function(ProdutoResource) {

    this.getProduto = function(id) {
        return ProdutoResource.get({id:id}).$promise;
    };

    this.getProdutos = function() {
        return ProdutoResource.query().$promise;
    };

    this.salvarProduto = function(produto) {
        if (produto.id){            
            return ProdutoResource.update({id : produto.id},produto).$promise;
        }else{
            return ProdutoResource.save(produto).$promise;
        }

    };

    this.excluir = function(produto) {
        return ProdutoResource.delete({id : produto.id}).$promise;
    };  
});
app.factory('ProdutoResource',function($resource){
         return $resource('http://localhost:8084/condominio_backend/webresources/model.produtos/:id',{},{
                    update:{
                        method:'PUT'
                    }})
});


app.controller('ListagemClienteControler',function($scope,ClienteService){
    listarClientes();

    function listarClientes(){
        ClienteService.getClientes().then(function(clientes){
            $scope.clientes = clientes;
        });
    };
    
    $scope.excluir = function(cliente) {
        ClienteService.excluir(cliente).then(function(){
            listarClientes();
        });
    };
});
app.controller('CadastroClienteControler',function($routeParams,$scope,$location,ClienteService){
    var id = $routeParams.id;
    
    if(id){
        ClienteService.getCliente(id).then(function(clientes){
            $scope.clientes = clientes;
        });
    }else{
        $scope.clientes = {};
    }
    
    $scope.salvar = function(cliente) {
        ClienteService.salvarCliente(cliente).then(function(){
            $location.path('listagem_cliente');
        });
        $scope.clientes = {};
    };

    $scope.cancelar = function(cliente) {
        $scope.clientes = {};
        $location.path('listagem_cliente');
    };
});
app.service('ClienteService',function(ClienteResource) {

    this.getCliente = function(id) {
        return ClienteResource.get({id:id}).$promise;
    };

    this.getClientes = function() {
        return ClienteResource.query().$promise;
    };

    this.salvarCliente = function(cliente) {
        if (cliente.id){            
            return ClienteResource.update({id : cliente.id},cliente).$promise;
        }else{
            return ClienteResource.save(cliente).$promise;
        }

    };

    this.excluir = function(cliente) {
        return ClienteResource.delete({id : cliente.id}).$promise;
    };  
});
app.factory('ClienteResource',function($resource){
         return $resource('http://localhost:8084/condominio_backend/webresources/model.cliente/:id',{},{
                    update:{
                        method:'PUT'
                    }})
});


app.controller('ListagemFornecedorControler',function($scope,Fornecedoreservice){
    listarFornecedores();

    function listarFornecedores(){
        Fornecedoreservice.getFornecedores().then(function(Fornecedores){
            $scope.fornecedores = Fornecedores;
        });
    };
    
    $scope.excluir = function(Fornecedor) {
        Fornecedoreservice.excluir(Fornecedor).then(function(){
            listarFornecedores();
        });
    };
});
app.controller('CadastroFornecedorControler',function($routeParams,$scope,$location,Fornecedoreservice){
    var id = $routeParams.id;
    
    if(id){
        Fornecedoreservice.getFornecedor(id).then(function(Fornecedores){
            $scope.fornecedores = Fornecedores;
        });
    }else{
        $scope.fornecedores = {};
    }
    
    $scope.salvar = function(Fornecedor) {
        Fornecedoreservice.salvarFornecedor(Fornecedor).then(function(){
            $location.path('listagem_fornecedor');
        });
        $scope.fornecedores = {};
    };

    $scope.cancelar = function(Fornecedor) {
        $scope.fornecedores = {};
        $location.path('listagem_fornecedor');
    };
});
app.service('Fornecedoreservice',function(FornecedorResource) {

    this.getFornecedor = function(id) {
        return FornecedorResource.get({id:id}).$promise;
    };

    this.getFornecedores = function() {
        return FornecedorResource.query().$promise;
    };

    this.salvarFornecedor = function(Fornecedor) {
        if (Fornecedor.id){            
            return FornecedorResource.update({id : Fornecedor.id},Fornecedor).$promise;
        }else{
            return FornecedorResource.save(Fornecedor).$promise;
        }

    };

    this.excluir = function(Fornecedor) {
        return FornecedorResource.delete({id : Fornecedor.id}).$promise;
    };  
});
app.factory('FornecedorResource',function($resource){
         return $resource('http://localhost:8084/condominio_backend/webresources/model.fornecedor/:id',{},{
                    update:{
                        method:'PUT'
                    }})
});



app.controller('ListagemPedidoControler',function($scope,Pedidoservice){
    listarPedidos();

    function listarPedidos(){
        Pedidoservice.getPedidos().then(function(Pedidos){
            $scope.Pedidos = Pedidos;
        });
    };
    
    $scope.excluir = function(Pedido) {
        Pedidoservice.excluir(Pedido).then(function(){
            listarPedidos();
        });
    };
});
app.controller('CadastroPedidoControler',function($routeParams,$scope,$location,Pedidoservice){
    var id = $routeParams.id;
    
    if(id){
        Pedidoservice.getPedido(id).then(function(Pedidos){
            $scope.Pedidos = Pedidos;
        });
    }else{
        $scope.Pedidos = {};
    }
    
    $scope.salvar = function(Pedido) {
        Pedidoservice.salvarPedido(Pedido).then(function(){
            $location.path('listagem_Pedido');
        });
        $scope.Pedidos = {};
    };

    $scope.cancelar = function(Pedido) {
        $scope.Pedidos = {};
        $location.path('listagem_Pedido');
    };
});
app.service('Pedidoservice',function(PedidoResource) {

    this.getPedido = function(id) {
        return PedidoResource.get({id:id}).$promise;
    };

    this.getPedidos = function() {
        return PedidoResource.query().$promise;
    };

    this.salvarPedido = function(Pedido) {
        if (Pedido.id){            
            return PedidoResource.update({id : Pedido.id},Pedido).$promise;
        }else{
            return PedidoResource.save(Pedido).$promise;
        }

    };

    this.excluir = function(Pedido) {
        return PedidoResource.delete({id : Pedido.id}).$promise;
    };  
});
app.factory('PedidoResource',function($resource){
         return $resource('http://localhost:8084/condominio_backend/webresources/model.pedidos/:id',{},{
                    update:{
                        method:'PUT'
                    }})
});


app.controller('ListagemVendaControler',function($scope,Vendaservice){
    listarVendas();

    function listarVendas(){
        Vendaservice.getVendas().then(function(Vendas){
            $scope.Vendas = Vendas;
        });
    };
    
    $scope.excluir = function(Venda) {
        Vendaservice.excluir(Venda).then(function(){
            listarVendas();
        });
    };
});
app.controller('CadastroVendaControler',function($routeParams,$scope,$location,Vendaservice){
    var id = $routeParams.id;
    
    if(id){
        Vendaservice.getVenda(id).then(function(Vendas){
            $scope.Vendas = Vendas;
        });
    }else{
        $scope.Vendas = {};
    }
    
    $scope.salvar = function(Venda) {
        Vendaservice.salvarVenda(Venda).then(function(){
            $location.path('listagem_Venda');
        });
        $scope.Vendas = {};
    };

    $scope.cancelar = function(Venda) {
        $scope.Vendas = {};
        $location.path('listagem_Venda');
    };
});
app.service('Vendaservice',function(VendaResource) {

    this.getVenda = function(id) {
        return VendaResource.get({id:id}).$promise;
    };

    this.getVendas = function() {
        return VendaResource.query().$promise;
    };

    this.salvarVenda = function(Venda) {
        if (Venda.id){            
            return VendaResource.update({id : Venda.id},Venda).$promise;
        }else{
            return VendaResource.save(Venda).$promise;
        }

    };

    this.excluir = function(Venda) {
        return VendaResource.delete({id : Venda.id}).$promise;
    };  
});
app.factory('VendaResource',function($resource){
         return $resource('http://localhost:8084/condominio_backend/webresources/model.vendas/:id',{},{
                    update:{
                        method:'PUT'
                    }})
});


app.controller('ListagemempresaControler',function($scope,empresaservice){
    listarempresas();

    function listarempresas(){
        empresaservice.getempresas().then(function(empresas){
            $scope.empresas = empresas;
        });
    };
    
    $scope.excluir = function(empresa) {
        empresaservice.excluir(empresa).then(function(){
            listarempresas();
        });
    };
});
app.controller('CadastroempresaControler',function($routeParams,$scope,$location,empresaservice){
    var id = $routeParams.id;
    
    if(id){
        empresaservice.getempresa(id).then(function(empresas){
            $scope.empresas = empresas;
        });
    }else{
        $scope.empresas = {};
    }
    
    $scope.salvar = function(empresa) {
        empresaservice.salvarempresa(empresa).then(function(){
            $location.path('listagem_empresa');
        });
        $scope.empresas = {};
    };

    $scope.cancelar = function(empresa) {
        $scope.empresas = {};
        $location.path('listagem_empresa');
    };
});
app.service('empresaservice',function(empresaResource) {

    this.getempresa = function(id) {
        return empresaResource.get({id:id}).$promise;
    };

    this.getempresas = function() {
        return empresaResource.query().$promise;
    };

    this.salvarempresa = function(empresa) {
        if (empresa.id){            
            return empresaResource.update({id : empresa.id},empresa).$promise;
        }else{
            return empresaResource.save(empresa).$promise;
        }

    };

    this.excluir = function(empresa) {
        return empresaResource.delete({id : empresa.id}).$promise;
    };  
});
app.factory('empresaResource',function($resource){
         return $resource('http://localhost:8084/condominio_backend/webresources/model.empresa/:id',{},{
                    update:{
                        method:'PUT'
                    }})
});


