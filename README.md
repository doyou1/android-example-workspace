# Sampling Project List
* [ActivityForResultProject](#ActivityForResultProject)
* [AlarmManagerSampling](#AlarmManagerSampling)
* [AnimationSampling](#AnimationSampling)
* [AppMsgProject](#AppMsgProject)
* [CalendarWithRecyclerViewSampling](#CalendarWithRecyclerViewSampling)
* [ChartSampling](#ChartSampling)
* [ColorPickerProject](#ColorPickerProject)
* [ComponentSizeAndPositionProject](#ComponentSizeAndPositionProject)
* [ContentProviderSampling](#ContentProviderSampling)
* [CustomComponentProject](#CustomComponentProject)
* [CustomDrawingSampling](#CustomDrawingSampling)
* [CustomInputLayoutSampling](#CustomInputLayoutSampling)
* [DatePickerProject](#DatePickerProject)
* [DialogActivityProject](#DialogActivityProject)
* [DpAndSpProject](#DpAndSpProject)
* [FCMProject](#FCMProject)
* [FCM_Token](#FCM_Token)
* [FCM_Topic](#FCM_Topic)
* [FragmentBackStackProject](#FragmentBackStackProject)
* [GallerySampling](#GallerySampling)
* [InfiniteViewPagerSampling](#InfiniteViewPagerSampling)
* [JodaTimeProject](#JodaTimeProject)
* [MqttPahoProject](#MqttPahoProject)
* [MultiFragmentInSingleActivitySampling](#MultiFragmentInSingleActivitySampling)
* [NavigationBackStackProject](#NavigationBackStackProject)
* [NewNetworkDataTestProject](#NewNetworkDataTestProject)
* [NotificationChannelSampling](#NotificationChannelSampling)
* [NumberPickerSampling](#NumberPickerSampling)
* [PedometerSampling](#PedometerSampling)
* [ProgressbarProject](#ProgressbarProject)
* [QnaProject](#QnaProject)
* [RecyclerViewScollProject](#RecyclerViewScollProject)
* [RegularIncomeAndInstallmentSampling](#RegularIncomeAndInstallmentSampling)
* [RoomDBImageSampling](#RoomDBImageSampling)
* [RoomDBSampling](#RoomDBSampling)
* [RoomSampling](#RoomSampling)
* [RyougaeSampling](#RyougaeSampling)
* [SeekbarProject](#SeekbarProject)
* [ServiceProject](#ServiceProject)
* [ServiceThreadExample](#ServiceThreadExample)
* [SwitchProject](#SwitchProject)
* [TransparentViewSampling](#TransparentViewSampling)
* [ViewPagerProject](#ViewPagerProject)
* [ViewPagerSampling](#ViewPagerSampling)
* [YoutubeAPIProject](#YoutubeAPIProject)
* [network-data-test-project](#network-data-test-project)
* [pedometerapp](#pedometerapp)
* [service-sampling](#service-sampling)

# [ActivityForResultProject](#ActivityForResultProject)
- ActivityForResult Launcherを利用してSubActivityからTextを受け取ってMainAcitivyに表示し、Galleryから写真を持ってきて表示する機能
- ActivityForResult Launcher를 이용해 SubActivity로부터 Text를 받아와 MainAcitivy에 표시하고, Gallery로부터 사진을 가져와 표시하는 기능
- Feature to receive text from SubActivity using ActivityForResult Launcher, display it in MainACTIVITY, and import and display photos from Gallery
- ./00_[ProjectResult]/ActivityForResultProject.mp4
<div><video controls src="https://user-images.githubusercontent.com/56281493/212525985-ea845f87-5aab-4bf8-aa4e-7cbdf0e3c8fb.mp4" muted="false"></video></div>

# [AlarmManagerSampling](#AlarmManagerSampling)
- Alarm ManagerとBroadCast Receiverを通じてNotification表示及びNotificationクリックすると希望するActivityに移動。BroadCast Receiverで設定したIntent Extraを該当Activityに表示する機能
- AlarmManager와 BroadCast Receiver를 통해 Notification 표시 및 Notification 클릭시 원하는 Activity로 이동. BroadCast Receiver에서 설정한 Intent Extra를 해당 Activity에 표시하는 기능
- Display Notification through Alarm Manager and BroadCast Receiver and click Notification to navigate to the desired activity. Feature to display Intent Extra set by the BroadCast Receiver in that activity
- ./00_[ProjectResult]/AlarmManagerSampling.mp4
<div><video controls src="https://user-images.githubusercontent.com/56281493/212528070-0e4ad395-ef52-4d2f-912a-c2db28164abb.mp4" muted="false"></video></div>

# [AnimationSampling](#AnimationSampling)
- ObjectAnimatorを活用してViewのアニメーション効果を具現。
- ObjectAnimator를 활용해 View의 애니메이션 효과를 구현.
- Use ObjectAnimator to realize the animation effect of View.
- ./00_[ProjectResult]/AnimationSampling.mp4
<div><video controls src="https://user-images.githubusercontent.com/56281493/212529376-909d1c40-2fd9-4295-b9af-f6a293358e50.mp4" muted="false"></video></div>

# [AppMsgProject](#AppMsgProject)
- Android で基本的に提供されるToast Text が見えにくい問題などがある。 ということでメッセージライブラリの一つをサンプリング。
- ライブラリの内容が簡単で、必要に応じてメッセージレイアウトを修正してCustomもできる。
- Android에서 기본적으로 제공되는 Toast Text가 잘 보이지 않는 문제 등이 있다. 그래서 메시지 라이브러리 중 하나 샘플링.
- 라이브러리 내용이 간단해 필요에 따라 메시지 레이아웃을 수정해 Custom도 할 수 있음.
- There is a problem that Toast Text, which is basically provided on Android, is difficult to see. So sampling one of the message libraries.
- The library contents are simple, so you can customize the message layout as needed.
- ./00_[ProjectResult]/AppMsgProject.mp4
<div><video controls src="https://user-images.githubusercontent.com/56281493/212533849-d8a828f4-df3f-4389-9523-0fc62845e886.mp4" muted="false"></video></div>

# [CalendarWithRecyclerViewSampling](#CalendarWithRecyclerViewSampling)
- 家計簿アプリのクローン中にカレンダーView機能の実装が必要で始めたサンプリングプロジェクト。
- 7*5サイズのカレンダーで内部に消費、所得、結果値を設定し、今日の日付の場合はViewのBackground Colorを埋めて強調した。
- 가계부 어플 클론하는 중 달력 View 기능 구현이 필요해서 시작한 샘플링 프로젝트.
- 7*5 크기의 달력으로 내부에 소비, 소득, 결과값을 설정하고, 오늘 날짜일 경우 View의 Background Color를 채워 강조했다.
- Sampling project started due to the need to implement calendar view function while cloning account book applications.
- Consumption, income, and results are set internally with a 7*5 calendar, and if it is today's date, it is highlighted by filling in View's Background Color.
- ./00_[ProjectResult]/CalendarWithRecyclerViewSampling.jpg
![CalendarWithRecyclerViewSampling](https://user-images.githubusercontent.com/56281493/212535146-21050c24-4e54-45a1-92d8-63f9c731a295.jpg)

# [ChartSampling](#ChartSampling)
- 家計簿アプリのクローン中に統計関連してチャートを作る場合が生じそうでサンプリングを進めた。
- Android で最も多く活用されるMPAndroid Chart を使用した。
- 가계부 어플 클론하는 중 통계관련해서 차트를 만들 경우가 생길 것 같아 샘플링을 진행했다.
- Android에서 가장 많이 활용되는 MPAndroidChart를 사용했다.
- Sampling was conducted because it seemed that there would be a case of making a chart related to statistics while cloning the accounting book application.
- We used MPandroidChart, which is the most widely used on Android.
- ./00_[ProjectResult]/ChartSampling.mp4
<div><video controls src="https://user-images.githubusercontent.com/56281493/212537145-4bd34666-3548-40be-88f9-b78711e702d1.mp4" muted="false"></video></div>

# [ColorPickerProject](#ColorPickerProject)
- 以前サンプリング作業をしながら進行したColor Pickerプロジェクト。
- LightテーマでDialogボタンが明確な色で表示されない問題があり、該当ライブラリ[github issue](https://github.com/Dhaval2404/ColorPicker/issues/16))を見たが、特に解決策はないようだ。
- 이전에 샘플링 작업을 하면서 진행했던 Color Picker 프로젝트.
- Light테마에서 Dialog 버튼이 명확한 색상으로 표시되지 않는 문제가 있어 해당 라이브러리 [github issue](https://github.com/Dhaval2404/ColorPicker/issues/16)를 봤으나, 딱히 해결방안은 없는 듯 보임.
- The Color Picker project that was previously carried out while sampling.
- I saw the library [github issue] (https://github.com/Dhaval2404/ColorPicker/issues/16) because the Dialog button is not clearly displayed in the light theme, but there seems to be no solution.
- ./00_[ProjectResult]/ColorPickerProject.mp4
<div><video controls src="https://user-images.githubusercontent.com/56281493/212549242-4b30ad6f-614f-4b6c-aa26-68b4ede26342.mp4" muted="false"></video></div>

# [ComponentSizeAndPositionProject](#ComponentSizeAndPositionProject)
- ScrollViewの最下段位置確認イベントの実装。 関連する計算ロジックは注釈により説明。
- ScrollView의 최하단 위치 확인 이벤트 구현. 관련한 계산 로직은 주석을 통해 설명.
- Implementation of ScrollView's lowest positioning event. The relevant computational logic is explained in the comments.
- ./00_[ProjectResult]/ComponentSizeAndPositionProject.mp4
<div><video controls src="https://user-images.githubusercontent.com/56281493/212914589-94cffa8f-85ff-4a5d-a6eb-46ab5719b093.mp4" muted="false"></video></div>

# [ContentProviderSampling](#ContentProviderSampling)
- ContentProviderProjectA: 簡単なDBとContentProvider具現(readable & writable)
- ContentProviderProjectB:ContentProviderProjectAのContentProviderにアクセスしてDBデータを抽出して表示。
- ContentProviderProjectA: 간단한 DB 구현과 ContentProvider 구현(readable & writable).
- ContentProviderProjectB: ContentProviderProjectA의 ContentProvider에 접근해 DB 데이터를 추출해서 표시.
ContentProviderProjectA: Implementation Simple DB and ContentProvider (readable & writable)
- ContentProviderProjectB: Access ContentProvider of ContentProviderProjectA to extract and display DB data.
- ./00_[ProjectResult]/ContentProviderSampling.mp4
<div><video controls src="https://user-images.githubusercontent.com/56281493/212921416-ea9a03c6-7ef2-436a-a056-19e2ce8f2b0a.mp4" muted="false"></video></div>

# [CustomComponentProject](#CustomComponentProject)
- ProgressBar、Circle ProgressBar、SeekBar、CheckBox、Switch関連のサンプリングプロジェクト。
- 関連してViewを活用するためにUpAndDownゲームを作成。
- ProgressBar, Circle ProgressBar, SeekBar, CheckBox, Switch 관련한 샘플링 프로젝트.
- 관련해 View를 활용보기 위해 UpAndDown 게임을 만듬.
- Sampling projects related to ProgressBar, Circle ProgressBar, SeekBar, CheckBox, Switch.
- In relation to this, I created an UpAndDown game to utilize View.
- ./00_[ProjectResult]/CustomComponentProject.mp4
<div><video controls src="https://user-images.githubusercontent.com/56281493/213180449-3a028244-0d9f-4be2-abf5-e503cb69d785.mp4
" muted="false"></video></div>

# [CustomDrawingSampling](#CustomDrawingSampling)
- View.onDrawを利用したCustom Viewサンプリング。
- 賭けによく使うはしごゲーム、ルーレット機能を具現してみた。
- View.onDraw를 이용한 Custom View 샘플링.
- 내기에 자주 사용하는 사다리 게임, 룰렛 기능을 구현해봤다.
- Custom View Sampling with View.onDraw.
- I implemented ladder games and roulette functions that are often used for betting.
- ./00_[ProjectResult]/CustomDrawingSampling.mp4
<div><video controls src="https://user-images.githubusercontent.com/56281493/213182730-0e5174f4-6170-42c1-8ec4-7fbff52beea1.mp4" muted="false"></video></div>

# [CustomInputLayoutSampling](#CustomInputLayoutSampling)
- 家計簿アプリの実装中に必要なエディットTextの新しい入力レイアウトの実装。
- EditText Focus時、キーボードではなく入力レイアウト表示及び関連作業遂行。BottomSheetDialogFragment活用.
- 가계부 어플 구현 중 필요한 EditText의 새로운 입력 레이아웃 구현.
- EditText Focus시, 키보드가 아닌 입력 레이아웃 표시 및 관련 작업 수행. BottomSheetDialogFragment 활용.
- Implementation of a new input layout of EditText required during the implementation of the diary application.
- During EditText Focus, displaying input layout and performing related tasks, not keyboards. With BottomSheetDialogFragment.
- ./00_[ProjectResult]/CustomInputLayoutSampling.mp4
<div><video controls src="https://user-images.githubusercontent.com/56281493/213188521-12c52c2b-faac-46a8-8ca3-75a5d4641a17.mp4" muted="false"></video></div>

# [DatePickerProject](#DatePickerProject)
- DatePickerに関連してString→Date、Date→String処理サンプリング。
- DialogFragment와 DatePickerDialog를 구현함. 今になって見ると、それほど必要なサンプリングではなかったような気もする。
- 内容が不十分すぎて、"AfterDate<PrevDate->PrevDate=AfterDate-1"、"PrevDate>AfterDate->AfterDate=PrevDate+1"に変更されるように処理を追加。
- DatePicker 관련해서 String -> Date, Date -> String 처리 샘플링.
- DialogFragment와 DatePickerDialog를 구현함. 지금 와서 보니 그리 필요한 샘플링이 아니었던 것 같기도 함.
- 내용이 너무 부실해서 "AfterDate < PrevDate -> PrevDate = AfterDate-1", "PrevDate > AfterDate -> AfterDate = PrevDate+1"로 변경되도록 처리 추가.
- String -> Date, Date -> String treatment sampling in relation to DatePicker.
- DialogFragment와 DatePickerDialog를 구현함. Now that I look at it, I don't think it was a necessary sampling.
- Added treatment to change to "AfterDate < PrevDate -> PrevDate = AfterDate-1", "PrevDate > AfterDate -> AfterDate = PrevDate+1" because the content is too poor.
- ./00_[ProjectResult]/DatePickerProject.mp4
<div><video controls src="https://user-images.githubusercontent.com/56281493/213461426-c785a857-a457-4292-b616-0062d91d7fa1.mp4" muted="false"></video></div>
  
# [DialogActivityProject](#DialogActivityProject)
- AppMsgを出力する機能を作成中、DialogFragmentでAppMsgを出力するとDialogの上に出力されるのではなく、下から出力される問題が発生。
- AppMsgがcontextを基準に出力される問題と見られる。 そこで、ActivityをDialogのように作ったDialogActivityを利用してAppMsgをDialogの上から出力されるようなトリックを与えた。
- 今後、透明アクティビティが必要になれば、Theme.Transparentを参考にすればいい。
- AppMsg를 출력하는 기능을 만들던 중, DialogFragment에서 AppMsg를 출력하면 Dialog 위에 출력되는 것이 아니라 밑에서 출력되는 문제가 발생.
- AppMsg가 context를 기준으로 출력되는 문제로 보여짐. 그래서 Activity를 Dialog와 같이 만든 DialogActivity를 이용해 AppMsg를 Dialog 위에서 출력되는 듯한 트릭을 주었다.
- 추후 투명 액티비티가 필요해지면 Theme.Transparent를 참고하면 되겠다.
- While creating a function to output AppMsg, when you output AppMsg from Dialog Fragment, the problem occurs that it is output from the bottom rather than on the Dialog.
- AppMsg appears to be a problem output based on context. So, using DialogActivity, which made Activity like Dialog, AppMsg was given a trick that seemed to be printed on Dialog.
- If you need transparent activities in the future, you can refer to Theme.Transparent.
- ./00_[ProjectResult]/DialogActivityProject.mp4
<div><video controls src="https://user-images.githubusercontent.com/56281493/213465470-b2a4c3a1-5b28-451c-96c3-47464dd8b128.mp4" muted="false"></video></div>
  
# [DpAndSpProject](#DpAndSpProject)
- 最初はDPとSpの違いを調べる概念のプロジェクト。
- dp(画面によるサイズ変化X)、sp(画面によるサイズ変化O)
- 内容が不十分で、dp、sp、in、mm、pt、pxまでサイズタイプを並べてサイズの違いを調べる。
- 처음엔 Dp와 Sp의 차이점을 조사하는 개념의 프로젝트.
- dp(화면에 따른 사이즈 변화 X), sp(화면에 따른 사이즈 변화 O)
- 내용이 부실해서 dp, sp, in, mm, pt, px까지 사이즈 타입을 나열해서 크기 차이를 살펴봄
- A project of the concept of investigating the difference between Dp and Sp.
- dp (size change X according to screen), sp (size change O according to screen)
- Because the content is poor, list the size types up to dp, sp, in, mm, pt, px and examine the size difference.
- ./00_[ProjectResult]/DpAndSpProject.mp4
<div><video controls src="https://user-images.githubusercontent.com/56281493/213724494-cbb7b491-0ca4-45bf-bb62-62912f7a07e5.mp4" muted="false"></video></div>
 
# [FCMProject](#FCMProject)
- FCMProject
# [FCM_Token](#FCM_Token)
- FCM_Token
# [FCM_Topic](#FCM_Topic)
- FCM_Topic
# [FragmentBackStackProject](#FragmentBackStackProject)
- プロジェクトをアップデートしながら、BackStackにあるFragmentに交換する作業を進めようとしたが、関連するAPIが見つからない。
- "support Fragment Manager.pop Back Stack"は、該当Fragmentの次のFragmentをすべてPOP! 消し止め
- 現在の機能が開発にどのように役立つのか見つからない。追加的な勉強が必要!!!
- 프로젝트 업데이트하면서 BackStack에 있는 Fragment로 교체하는 작업을 진행하려했는데, 관련된 API를 찾지 못함.
- "supportFragmentManager.popBackStack"는 해당Fragment의 다음 Fragment들을 모두 POP! 없애버림.
- 현재 기능이 개발에 어떤 도움이 되는지 찾지 못함. !!!추가적인 공부 필요!!!
- While updating the project, I tried to replace it with a fragment in BackStack, but I couldn't find the related API.
- "supportFragmentManager.popBackStack" POP all of the following fragments of the corresponding fragment! getting rid of
- Couldn't find out how the current feature is helping development. !!!Need additional study!!!
- ./00_[ProjectResult]/FragmentBackStackProject.mp4
<div><video controls src="https://user-images.githubusercontent.com/56281493/213921768-2e795b57-861f-4d76-b150-17a85a5a2002.mp4" muted="false"></video></div>

# [GallerySampling](#GallerySampling)
- GallerySampling
# [InfiniteViewPagerSampling](#InfiniteViewPagerSampling)
- InfiniteViewPagerSampling
# [JodaTimeProject](#JodaTimeProject)
- JodaTimeProject
# [MqttPahoProject](#MqttPahoProject)
- MqttPahoProject
# [MultiFragmentInSingleActivitySampling](#MultiFragmentInSingleActivitySampling)
- MultiFragmentInSingleActivitySampling
# [NavigationBackStackProject](#NavigationBackStackProject)
- NavigationBackStackProject
# [NewNetworkDataTestProject](#NewNetworkDataTestProject)
- NewNetworkDataTestProject
# [NotificationChannelSampling](#NotificationChannelSampling)
- NotificationChannelSampling
# [NumberPickerSampling](#NumberPickerSampling)
- NumberPickerSampling
# [PedometerSampling](#PedometerSampling)
- PedometerSampling
# [ProgressbarProject](#ProgressbarProject)
- ProgressbarProject
# [QnaProject](#QnaProject)
- QnaProject
# [RecyclerViewScollProject](#RecyclerViewScollProject)
- RecyclerViewScollProject
# [RegularIncomeAndInstallmentSampling](#RegularIncomeAndInstallmentSampling)
- RegularIncomeAndInstallmentSampling
# [RoomDBImageSampling](#RoomDBImageSampling)
- RoomDBImageSampling
# [RoomDBSampling](#RoomDBSampling)
- RoomDBSampling
# [RoomSampling](#RoomSampling)
- RoomSampling
# [RyougaeSampling](#RyougaeSampling)
- RyougaeSampling
# [SeekbarProject](#SeekbarProject)
- SeekbarProject
# [ServiceProject](#ServiceProject)
- ServiceProject
# [ServiceThreadExample](#ServiceThreadExample)
- ServiceThreadExample
# [SwitchProject](#SwitchProject)
- SwitchProject
# [TransparentViewSampling](#TransparentViewSampling)
- TransparentViewSampling
# [ViewPagerProject](#ViewPagerProject)
- ViewPagerProject
# [ViewPagerSampling](#ViewPagerSampling)
- ViewPagerSampling
# [YoutubeAPIProject](#YoutubeAPIProject)
- YoutubeAPIProject
# [network-data-test-project](#network-data-test-project)
- network-data-test-project
# [pedometerapp](#pedometerapp)
- pedometerapp
# [service-sampling](#service-sampling)
- service-sampling
