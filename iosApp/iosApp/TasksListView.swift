import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI
import Combine

struct TasksListView: View {
    @StateViewModel var viewModel = TaskViewModel()
    @StateViewModel var weekViewModel = WeekViewModel()
    @Environment(\.colorScheme) var colorScheme
    @Binding var origin: Bool
    @Binding var deleteView: Bool
    @Binding var showTabView: Bool
    @State var filteredTasks: [Models.Task] = []
    
    init(origin: Binding<Bool>, deleteView: Binding<Bool>, showTabView: Binding<Bool>){
        self._origin = origin
        self._deleteView = deleteView
        self._showTabView = showTabView
        self._filteredTasks = State(initialValue: viewModel.getTasks(number: weekViewModel.selectedDay.number, month: weekViewModel.selectedDay.month))
    }

    var body: some View {
        ScrollView {
            VStack(alignment: .leading) {
                ForEach(filteredTasks, id: \.id) { task in
                    VStack {
                        HStack {
                            NavigationLink(destination: DeleteForm(task: task, origin: $origin).environmentObject(viewModel), isActive: $deleteView) {
                                EmptyView()
                            }
                            .hidden()
                            
                            Button(action: {
                                deleteView = true
                                showTabView = false
                            }){
                                VStack(alignment: .leading) {
                                    Text(task.title)
                                        .font(.footnote)
                                        .fontWeight(.medium)
                                        .foregroundColor(colorScheme == .light ? .black : .white)
                                    Text(task.category)
                                        .font(.footnote)
                                        .foregroundColor(Color(white: 0.4))
                                        .fontWeight(.medium)
                                    Text(task.details)
                                        .font(.footnote)
                                        .foregroundColor(Color(white: 0.6))
                                }
                            }
                            Spacer()
                            
                            Button(action: {
                                viewModel.finishPendingTask(taskId: task.id)
                                weekViewModel.setCurrentWeek(day: weekViewModel.selectedWeek.week[0].number, monthName: weekViewModel.selectedWeek.week[0].month)
                            }) {
                                HStack {
                                    if (task.inProgress){
                                        Image(systemName: "stop.fill")
                                            .font(.footnote)
                                            .foregroundColor(colorScheme == .light ? .black : .white)
                                    } else {
                                        Image(systemName: "play.fill")
                                            .font(.footnote)
                                            .foregroundColor(colorScheme == .light ? .black : .white)
                                    }
                                    Text(task.hours)
                                        .font(.footnote)
                                        .foregroundColor(colorScheme == .light ? .black : .white)
                                }
                                .padding(6)
                            }
                            .background(colorScheme == .light ? Color.white : Color(red: 0.1, green: 0.1, blue: 0.1))
                            .overlay(
                                RoundedRectangle(cornerRadius: 4)
                                    .stroke(Color(red: 0.8, green: 0.8, blue: 0.8), lineWidth: 1)
                            )
                        }
                        .frame(maxWidth: .infinity)
                        .padding()
                    }
                    .background(colorScheme == .light ? Color.white : Color(red: 0.1, green: 0.1, blue: 0.1))
                    .cornerRadius(10)
                    .padding(.vertical, 5)
                    .frame(maxWidth: .infinity)
                }
            }
            .padding(.horizontal, 20)
        }
        .onChange(of: weekViewModel.selectedDay) { _ in
            filteredTasks = viewModel.getTasks(number: weekViewModel.selectedDay.number, month: weekViewModel.selectedDay.month)
        }
    }
}
