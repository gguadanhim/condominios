/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var app = angular.module('usuarioApp',['ngRoute','ngResource']);

var token = '';
var empresa = {};

app.config(function($routeProvider){
    
    $routeProvider.when('/cadastro_usuario',{
        controller: 'CadastroUsuarioControler',
        templateUrl : 'templates/cadastro_usuario.html'
    }).when('/cadastro_usuario/:id/model.usuario/:idusuario',{
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
        
     }).when('/login',{
        controller: 'LoginControler',
        templateUrl : 'login.html'
     }).when('/logof',{
        controller: 'LogofControler',
        templateUrl : 'login.html'
     }).when('/inicio',{
        controller: 'InicioControler',
        templateUrl : 'templates/inicio.html'
        
    }).otherwise('/login');
});


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
    var id = $routeParams.idusuario;
    if(empresa.id == 0){
        empresa.id = $routeParams.id;
    }
    $scope.empresa = empresa;
    
    if((id) && (id!=0)){
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
        return UsuariosResource.BuscarUsuario({id:empresa.id},{idusuario:id}).$promise;
    };

    this.getUsuarios = function() {
        return UsuariosResource.BuscarUsuarios({id : empresa.id}).$promise;
    };

    this.salvarUsuario = function(usuario) {
        if (usuario.id){            
            return UsuariosResource.AtualizarUsuario({id : empresa.id, idusuario : usuario.id}, usuario).$promise;
        }else{
            return UsuariosResource.adicionarUsuario({id : empresa.id},usuario).$promise;
        }
    };

    this.excluir = function(usuario) {
        return UsuariosResource.ApagarUsuario({id : empresa.id} , {idusuario : usuario.id}).$promise;
    };  
});
app.factory('UsuariosResource',function($resource){
         var Url = 'http://localhost:8084/condominio_backend/webresources/model.empresa/:id';
         return $resource(Url,{},{
                    AtualizarUsuario:{
                        method:'PUT',
                        url: Url + '/model.usuario/:idusuario',
                        params:{
                            id:'@id',
                            idusuario:'@idusuario'
                        }
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
                        url: Url + '/model.usuario/:idusuario',
                        params:{
                            id:'@id',
                            idusuario:'@idusuario'
                        }
                    },
                    ApagarUsuario:{
                        method:'DELETE',
                        url: Url + '/model.usuario/:idusuario',
                        params:{
                            id:'@id',
                            idusuario:'@idusuario'
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
        return ProdutoResource.BuscarProduto({idempresa:empresa.id},{id:id}).$promise;
    };

    this.getProdutos = function() {
        return ProdutoResource.BuscarProdutos({idempresa:empresa.id}).$promise;
    };

    this.salvarProduto = function(produto) {
        if (produto.id){            
            return ProdutoResource.AtualizarProduto({idempresa:empresa.id, id : produto.id},produto).$promise;
        }else{
            return ProdutoResource.adicionarProduto({idempresa:empresa.id},produto).$promise;
        }
    };

    this.excluir = function(produto) {
        return ProdutoResource.ApagarProduto({idempresa:empresa.id},{id : produto.id}).$promise;
    };  
});
app.factory('ProdutoResource',function($resource){
         var Url = 'http://localhost:8084/condominio_backend/webresources/model.produtos/:idempresa';
         return $resource(Url,{},{
                    AtualizarProduto:{
                        method:'PUT',
                        url: Url + '/produto/:id',
                        params:{
                            idempresa:'idempresa',
                            id:'@id'
                            
                        }
                    },
                    adicionarProduto:{
                        method:'POST',
                        url: Url + '/produto',
                        params:{
                            idempresa:'@idempresa'
                        }
                    },
                    BuscarProdutos:{
                        method:'GET',
                        isArray:true,
                        url: Url + '/produto',
                        params:{
                            idempresa:'@idempresa'
                        }
                    },
                    BuscarProduto:{
                        method:'GET',
                        isArray:false,
                        url: Url + '/produto/:id',
                        params:{
                            idempresa:'@idempresa',
                            id:'@id'
                        }
                    },
                    ApagarProduto:{
                        method:'DELETE',
                        url: Url + '/produto/:id',
                        params:{
                            idempresa:'@idempresa',
                            id:'@id'
                        }
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
        return ClienteResource.BuscarCliente({idempresa:empresa.id},{id:id}).$promise;
    };

    this.getClientes = function() {
        return ClienteResource.BuscarClientes({idempresa:empresa.id}).$promise;
    };

    this.salvarCliente = function(cliente) {
        if (cliente.id){            
            return ClienteResource.AtualizarCliente({idempresa:empresa.id , id : cliente.id},cliente).$promise;
        }else{
            return ClienteResource.adicionarCliente({idempresa:empresa.id},cliente).$promise;
        }

    };

    this.excluir = function(cliente) {
        return ClienteResource.ApagarCliente({idempresa:empresa.id},{id : cliente.id}).$promise;
    };  
});
app.factory('ClienteResource',function($resource){
         var Url = 'http://localhost:8084/condominio_backend/webresources/model.cliente/:idempresa';
         return $resource(Url,{},{
                    AtualizarCliente:{
                        method:'PUT',
                        url: Url + '/cliente/:id',
                        params:{
                            idempresa:'idempresa',
                            id:'@id'
                            
                        }
                    },
                    adicionarCliente:{
                        method:'POST',
                        url: Url + '/cliente',
                        params:{
                            idempresa:'@idempresa'
                        }
                    },
                    BuscarClientes:{
                        method:'GET',
                        isArray:true,
                        url: Url + '/cliente',
                        params:{
                            idempresa:'@idempresa'
                        }
                    },
                    BuscarCliente:{
                        method:'GET',
                        isArray:false,
                        url: Url + '/cliente/:id',
                        params:{
                            idempresa:'@idempresa',
                            id:'@id'
                        }
                    },
                    ApagarCliente:{
                        method:'DELETE',
                        url: Url + '/cliente/:id',
                        params:{
                            idempresa:'@idempresa',
                            id:'@id'
                        }
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
        return FornecedorResource.BuscarFornecedor({idempresa:empresa.id},{id:id}).$promise;
    };

    this.getFornecedores = function() {
        return FornecedorResource.BuscarFornecedores({idempresa:empresa.id}).$promise;
    };

    this.salvarFornecedor = function(Fornecedor) {
        if (Fornecedor.id){            
            return FornecedorResource.AtualizarFornecedor({idempresa:empresa.id , id : Fornecedor.id},Fornecedor).$promise;
        }else{
            return FornecedorResource.adicionarFornecedor({idempresa:empresa.id},Fornecedor).$promise;
        }

    };

    this.excluir = function(Fornecedor) {
        return FornecedorResource.ApagarFornecedor({idempresa:empresa.id},{id : Fornecedor.id}).$promise;
    };  
});
app.factory('FornecedorResource',function($resource){
         var Url = 'http://localhost:8084/condominio_backend/webresources/model.fornecedor/:idempresa';
         return $resource(Url,{},{
                    AtualizarFornecedor:{
                        method:'PUT',
                        url: Url + '/fornecedor/:id',
                        params:{
                            idempresa:'idempresa',
                            id:'@id'
                            
                        }
                    },
                    adicionarFornecedor:{
                        method:'POST',
                        url: Url + '/fornecedor',
                        params:{
                            idempresa:'@idempresa'
                        }
                    },
                    BuscarFornecedores:{
                        method:'GET',
                        isArray:true,
                        url: Url + '/fornecedor',
                        params:{
                            idempresa:'@idempresa'
                        }
                    },
                    BuscarFornecedor:{
                        method:'GET',
                        isArray:false,
                        url: Url + '/fornecedor/:id',
                        params:{
                            idempresa:'@idempresa',
                            id:'@id'
                        }
                    },
                    ApagarFornecedor:{
                        method:'DELETE',
                        url: Url + '/fornecedor/:id',
                        params:{
                            idempresa:'@idempresa',
                            id:'@id'
                        }
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
        return PedidoResource.BuscarPedido({idempresa:empresa.id},{id:id}).$promise;
    };

    this.getPedidos = function() {
        return PedidoResource.BuscarPedidos({idempresa:empresa.id}).$promise;
    };

    this.salvarPedido = function(Pedido) {
        if (Pedido.id){            
            return PedidoResource.AtualizarPedido({idempresa:empresa.id , id : Pedido.id},Pedido).$promise;
        }else{
            return PedidoResource.adicionarPedido({idempresa:empresa.id},Pedido).$promise;
        }

    };

    this.excluir = function(Pedido) {
        return PedidoResource.ApagarPedido({idempresa:empresa.id},{id : Pedido.id}).$promise;
    };  
});
app.factory('PedidoResource',function($resource){
         var Url = 'http://localhost:8084/condominio_backend/webresources/model.pedidos/:idempresa';
         return $resource(Url,{},{
                    AtualizarPedido:{
                        method:'PUT',
                        url: Url + '/pedido/:id',
                        params:{
                            idempresa:'idempresa',
                            id:'@id'
                            
                        }
                    },
                    adicionarPedido:{
                        method:'POST',
                        url: Url + '/pedido',
                        params:{
                            idempresa:'@idempresa'
                        }
                    },
                    BuscarPedidos:{
                        method:'GET',
                        isArray:true,
                        url: Url + '/pedido',
                        params:{
                            idempresa:'@idempresa'
                        }
                    },
                    BuscarPedido:{
                        method:'GET',
                        isArray:false,
                        url: Url + '/pedido/:id',
                        params:{
                            idempresa:'@idempresa',
                            id:'@id'
                        }
                    },
                    ApagarPedido:{
                        method:'DELETE',
                        url: Url + '/pedido/:id',
                        params:{
                            idempresa:'@idempresa',
                            id:'@id'
                        }
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
        return VendaResource.BuscarVenda({idempresa:empresa.id},{id:id}).$promise;
    };

    this.getVendas = function() {
        return VendaResource.BuscarVendas({idempresa:empresa.id}).$promise;
    };

    this.salvarVenda = function(Venda) {
        if (Venda.id){            
            return VendaResource.AtualizarVenda({idempresa:empresa.id , id : Venda.id},Venda).$promise;
        }else{
            return VendaResource.adicionarVenda({idempresa:empresa.id},Venda).$promise;
        }

    };

    this.excluir = function(Venda) {
        return VendaResource.ApagarVenda({idempresa:empresa.id},{id : Venda.id}).$promise;
    };  
});
app.factory('VendaResource',function($resource){
         var Url = 'http://localhost:8084/condominio_backend/webresources/model.vendas/:idempresa';
         return $resource(Url,{},{
                    AtualizarVenda:{
                        method:'PUT',
                        url: Url + '/venda/:id',
                        params:{
                            idempresa:'idempresa',
                            id:'@id'
                            
                        }
                    },
                    adicionarVenda:{
                        method:'POST',
                        url: Url + '/venda',
                        params:{
                            idempresa:'@idempresa'
                        }
                    },
                    BuscarVendas:{
                        method:'GET',
                        isArray:true,
                        url: Url + '/venda',
                        params:{
                            idempresa:'@idempresa'
                        }
                    },
                    BuscarVenda:{
                        method:'GET',
                        isArray:false,
                        url: Url + '/venda/:id',
                        params:{
                            idempresa:'@idempresa',
                            id:'@id'
                        }
                    },
                    ApagarVenda:{
                        method:'DELETE',
                        url: Url + '/venda/:id',
                        params:{
                            idempresa:'@idempresa',
                            id:'@id'
                        }
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
    
    $scope.abrirCadastroUsuario = function(empresa){
        $location.path('#!/cadastro_usuario/'+empresa.id+'/model.usuario/');      
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

app.controller('InicioControler',function($scope, $http, $rootScope,$location){
    $scope.teste = function () {
        
    }
});

app.controller('LogofControler',function($scope, $http, $rootScope,$location){
    token = '';
    empresa.id = 0;
    $rootScope.currentUserSignedIn = false;
    
    $location.path('/login');
});

app.controller('LoginControler',function($scope, $http, $rootScope,$location){
    
    $scope.SendData = function () {
        var config = {
            headers : {
                'Content-Type': 'application/json;charset=utf-8;'
            }
        }

        $http.post('http://localhost:8084/condominio_backend/webresources/model.usuario/login', $scope.user, config)
        .then(function successCallback(response) {
            token = response.data.token;
            empresa.id = response.data.empresa;
            $rootScope.currentUserSignedIn = true;
            $location.path('/inicio');
            
        }, function errorCallback(response) {
            alert('erro');
            $scope.PostDataResponse = "Data: " + response.data +
                "<hr />status: " + response.status +
                "<hr />headers: " + response.header +
                "<hr />config: " + config;
        });
    };
});


app.config(function ($httpProvider){
   $httpProvider.interceptors.push('InterceptorToken'); 
});

app.factory("InterceptorToken",function($location,$rootScope){
   return {
     request: function(config){
         config.headers['Authorization'] = token;
         config.headers['empresa'] = empresa.id;
         return config;
     },
     responseError: function(response) {
      if (response.status === 401 || response.status === 403) {
        $location.path('/login');
        $rootScope.currentUserSignedIn = false;
      }
    }
   };
});