# mysunshine
This is the default branch for this project, containing the latest version of all files.

#Learned Lessons
Android Apps

#######################################################
Interfaces e Atividades

Activity
o	É o ponto de interação com o usuário;
o	É declarado no Manifest File;
o	Possui um Persistent State e Preferences;
o	Possui um Layout;
o	Possui Fragments;
o	Possui um Menu;
o	Possui um FragmentManager;
o	Possui Permissions.

Fragment
o	É um pedaço de uma Activity;
o	Associado a um Layout  Activity;
o	É gerenciado por um FragmentManager;
o	É descrito por um arquivo XML (res/layout);
o	Possui um ID;
o	Possui um Menu.
o	Subclasses: PreferencesFragment, ErrorFragment, DetailsFragment

View
o	É o último elemento de interface 
o	É descrito por um arquivo XML (res/layout);
o	Possui um ID;
o	Ouve e trata eventos gerais: click, focus, hover, etc.
o	Subclasses: TextView, ViewGroup, ImageView, KeyboardView, ProgressBar, etc.

ViewGroup
o	É um agrupamento de Views;
o	É descrito por um arquivo XML (res/layout);
o	Subclasses: FrameLayout, GridLayout, LinearLayout, AdapterView, SlidingPanelayout, Toolbar, TvView, etc.

AdapterView
o	É uma View cujos dados são definidos por um Adapter;
o	Ouve e trata eventos específicos de lista: 
o	Subclasses: ListView, Spinner, ListView, Gallery, etc.

ListView
o	É uma AdapterView que mostra itens em uma lista vertical;
o	Esta associado a um ArrayAdapter ou CursorAdapter;
o	É descrito por um arquivo XML (res/layout);
o	Possui um ID.

ArrayAdapter
o	É um Adapter suportado por um Array de Objects arbitrários.
o	Possui referência a um resource id (res/layout) com um único TextView.
o	Preenche o TextView com o conteúdo do toString() de cada Object.

CursorAdapter
o É um adaptador que passa o conteúdo de um cursor para um objeto do tipo ListView
o O cursor deve conter uma coluna _ID;
o Precisa ter a implementação dos métodos newView e bindView;
o O método newView infla a view correspondente à posição do cursor, que pode ser obtida pelo uso do método getPosition;
o O número de opções de layouts disponíveis deve ser passado sobrepondo o método getViewTypeCount;
o A seleção do tipo de layout mais adequado deve ser feita sobrepondo o método getItemViewType;
o O método bindView configura as informações das View (setText, setImageResource) com os dados do cursor;
o Pode implementar um ViewHolder, a fim de evitar chamadas repetidas de findViewById.

#######################################################
Configurações e Preferências

PreferenceActivity (usada para API 10 ou anterior)
o	É a atividade de base para mostrar preferências ao usuário.
o	Possui headers, associados a PreferenceFragment exclusivos para opções daquele header específico;
o	Possui um layout descrito via XML, com preference_headers;
o	Deve implementar o método onBuildHeaders(List).

Preference
o	É o elemento básico de interface de um PreferenceActivity ou PreferenceFragment;
o	Está associado a um objeto de SharedPreferences;
o	Esta associado a um Context;
o	Possui uma key, que será a referência do SharedPrefrences;
o	É decrito por um arquivo XML (res/xml);
o São agrupados em <PreferenceCategory> ou <PreferenceScreen>;
o Pode ser associado a um Intent, associado a eventos OnClick;
o	Subclasses: CheckBoxPreference, EditTextPreference, ListPreference, PreferenceScreen, SwitchPreference, etc.

SharedPreferences
o É uma referência a um arquivo de preferências, com AVP definidos por keys;
o Podem ser obtidos através do método PreferenceManager.getDefaultSharedPredferences;

#######################################################
Tarefas Assíncronas

Async Task
o É uma classe abstrata que roda em outro thread de processamento;
o Define os métodos doInBackground e onPostExecute;
o doInBackground recebe como argumento um Array de objetos de um tipo pré-definido;
o onPostExecute recebe o output do doInBackground e roda no thread principal;
o Recebe parâmetros através de um comando execute

#######################################################
ActionProviders

ShareActionProvider
o É uma subclasse de ActionProvider que gerencia o compartilhamento de informações com outros aplicativos;
o Normalmente associado a um MenuItem com parâmetro app:actionProviderClass e showAsAction;
o Requer a configuração de um tema compatível com AppCompat;
o O Intent do ShareActionProvider deve ter o flag FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET, para voltar à atividade original.

#######################################################
Intent
o action.SEND para enviar informações a outra Activity;
o action.VIEW para visualizar informações específicas, tais como páginas ou mapas;
o Recebe e envia dados em campos EXTRA.

Intent-filter
o category.LAUNCHER para aparecer entre os demais apps;
o category.DEFAULT para ser elegível a Intent implícito;
o Precisa implementar todas as categorias de um Intent implícito para ser elegível.

Activity Lifecycle
o onCreate -> Created -> onStart -> Visible -> onResume -> Focused;
o onPause -> Lost focus -> onStop -> Not visible -> onDestroy -> Not running;
o Aplicações podem ser terminadas sem aviso, após a chamada do método onPause(). Salvar informações do assinante.

#######################################################
Internet Connections

Connection
o Uri.Builder constroi URI com scheme, authority, path e query parameters.
o Uri são construídas com o método Urí.Builder.build();
o URL são construídas com os método URL(Uri.toString);
o HttpURLConnection são criadas com o URL.openConnection() e tem o método definido por .setRequestMethod(String);
o HttpURLConnection possuem o método .connect() e .getInputStream();
o Dados são obtidos através de InputStream, passado para um InputStreamReader, que por sua vez é entregue a um BufferedReader.
o As linhas lidas do BufferedReader são usadas na construção de uma String com um StringBuilder.
o A entrada JSON é obtidas com um StringBuilder.toString().

JSON Parsing
o JSONObject são criados a partir de String;
o JSONObject são delimitados por {} e tem chaves exclusivas no AVP;
o JSONArray são Arrays de JSONObject e outros AVP, delimitado por [] e pode ter repetição de valores na sua hierarquia.

#######################################################
Receivers
o Ouve broadcasts enviados por outras aplicações. Implementa a classe BroadcastReceiver;
o Sobrescreve pelo menos o método chamado onReceive;
o Deve se registrar, via uma entrada no Manifest, ou dinamicamente via registerReceiver;
o Está associado a um intent-filter, que diz em quais eventos a app está interessada.

#######################################################
SQLite
o Linguagem de programação para armazenar dados estruturados das aplicações;
o Base de dados contém um Contract, que define tabelas e colunas das bases de dados;
o São criadas a partir da classe SQLiteOpenHelper.

SQLiteOpenHelper
o É um auxiliar na criação de bases de dados;
o As bases são de fato craiadas dentro do método .onCreate;
o A base é criada com o comando sqLiteDatabase.execSQL(String);
o Mudanças na tabela ou nas suas colunas devem sempre atualizar a versão. São realizadas no método .onUpgrade

SQLiteQueryBuilder
o É uma classe que ajuda a montar uma query SQL;
o .setTables define as tabelas que serão consultadas;
o .query executa a consulta conforme a projection, selecion e selection args.

ContentValues
o É um conjunto de valores de diversos formatos;
o É usado para escrever em bases de dados no Android;
o Estrutura simples, com métodos get() e put().

#######################################################
Android Tests

TestSuite
o Conjunto de testes a serem executados;
o Deve sobrescrever o método public static Test suite(), que será usado pelo junit como coleção dos testes a serem feitos;
o É construído através de um TestSuiteBuilder;
o É populado pelo método .includeAllPackagesUnderHere() e construído pelo método .build().

AndroidTestCase
o Caso de teste usado quando é necessário receber um contexto, como, por exemplo, para acessar bases de dados;
o Estende a classe TestCase;
o Recebe uma variável mContext, que referencia o contexto;
o Todos os testes devem ser definidos por métodos com nome iniciado por 'test';
o Deve sobrescrever os métodos setUp() e tearDown().

#######################################################
Content Providers
o Provem métodos para enviar e receber dados de outras aplicações;
o São construídos em 4 etapas: Determinar URIs, Atualizar o Contrato, Preencher o URIMatcher e Implementar Funções;
o content://com.example.master.mysunshine/Weather/94043?DATE=1435104000
o scheme = content;
o authority = com.example.master.mysunshine;
o Possui constantes int para cada um dos paths;
o Contém um UriMatcher para simplificar a identificação com as Uri;
o Contém um SQLOpenHelper para ajudar abrir as bases SQLite;
o Contém um SQLiteQueryBuilder para ajudar a fazer as queries SQL;
o Contém Strings de suporte para realizar as queries SQL;
o Contém as funções para fazer as queries na nase de dados;
o Sobrescreve as funções: onCreate (inicializa o SQLOpenHelper), getType (devolve o tipo do conteúdo), query (busca valores na base de dados), insert (insere ContentValues na base de dados), delete (apaga uma linha no banco de dados), update (atualiza valores no banco de dados) e shutdown (fecha a base de dados). Além disso sobrescreve o método bulkInsert (insere um Array de ContentValues).

ContentProvider Contract
o Possui subclasses que estem BaseColumns, com nomes para as colunas das nossas tabelas;
o Possui a definição de uma CONTENT_URI e uma string de CONTENT_TYPE;
o Contém funções que ajudam a construir as queries do ContentProvider.

Content Resolver
o Ferramenta que ajuda os requests a encontrar os ContentProviders corretos;
o Possui 4 métodos: query (ler), insert (adicionar), update (atualizar) e delete (apagar).

UriMatcher
o Classe de apoio para comparar URIs em ContentProviders;
o Facilita realizar o match entre o path de uma Uri com os valores pré-definidos.

#######################################################
Loaders

o São identificados por um identificador (int) único;
o Requer a implementação da interface LoaderManager.LoaderCallbacks<Cursor>;
o É iniciado durante o onActivityCreated com o método getLoaderManager().initLoader;
o Precisa sobrepor o método onCreateLoader, que retorna um CursorLoader;
o Precisa sobrepor o método onLoadFinished, que atualiza o cursor do CursorAdapter;
o Precisa sobrepor o método onLoaderReset, que limpa o cursor do CursorAdapter.


