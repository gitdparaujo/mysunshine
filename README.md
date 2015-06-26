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
o São agrupados em <PreferenceCategory> ou <PreferenceScreen>
o	Subclasses: CheckBoxPreference, EditTextPreference, ListPreference, PreferenceScreen, SwitchPreference, etc.



