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
o	Esta associado a um ArrayAdapter;
o	É descrito por um arquivo XML (res/layout);
o	Possui um ID.

ArrayAdapter
o	É um Adapter suportado por um Array de Objects arbitrários.
o	Possui referência a um resource id (res/layout) com um único TextView.
o	Preenche o TextView com o conteúdo do toString() de cada Object.

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
o São criados dentro do método onCreate(SQLiteDatabase sqLiteDatabase);
o A base é criada com o comando sqLiteDatabase.execSQL(String);

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



