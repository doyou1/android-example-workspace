# Sampling Project List
* [ActivityForResultProject](#ActivityForResultProject)
* [AlarmManagerSampling](#AlarmManagerSampling)
* [AnimationSampling](#AnimationSampling)
* [AppMsgProject](#AppMsgProject)
* [CalendarWithRecyclerViewSampling](#CalendarWithRecyclerViewSampling)
* [ChartSampling](#ChartSampling)
* [ColorPickerProject](#ColorPickerProject)
* [ComponentSizeAndPositionProject](#ComponentSizeAndPositionProject)
* [ContentProviderProjectA](#ContentProviderProjectA)
* [ContentProviderProjectB](#ContentProviderProjectB)
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
<div><video controls src="" muted="false"></video></div>


# [ContentProviderProjectA](#ContentProviderProjectA)
- ContentProviderProjectA
# [ContentProviderProjectB](#ContentProviderProjectB)
- ContentProviderProjectB
# [CustomComponentProject](#CustomComponentProject)
- CustomComponentProject
# [CustomDrawingSampling](#CustomDrawingSampling)
- CustomDrawingSampling
# [CustomInputLayoutSampling](#CustomInputLayoutSampling)
- CustomInputLayoutSampling
# [DatePickerProject](#DatePickerProject)
- DatePickerProject
# [DialogActivityProject](#DialogActivityProject)
- DialogActivityProject
# [DpAndSpProject](#DpAndSpProject)
- DpAndSpProject
# [FCMProject](#FCMProject)
- FCMProject
# [FCM_Token](#FCM_Token)
- FCM_Token
# [FCM_Topic](#FCM_Topic)
- FCM_Topic
# [FragmentBackStackProject](#FragmentBackStackProject)
- FragmentBackStackProject
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
