import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

struct TaskListView: View {
    @StateViewModel var formViewModel = FormViewModel()
    let clients = Task().getTaskTypes()

    var body: some View {
        VStack {
            TextField("Search by task name", text: .constant(""))
                .padding()
                .background(Color(.systemGray6))
                .cornerRadius(10)
                .padding()

            List(clients, id: \.self) { taskTitle in
                HStack {
                    Text(taskTitle)
                    Spacer()
                    if taskTitle == formViewModel.selectedTask {
                        Image(systemName: "checkmark")
                            .foregroundColor(.green)
                    }
                }
                .contentShape(Rectangle())
                .onTapGesture {
                    formViewModel.setSelectedTask(task: taskTitle)
                }
            }
            .navigationTitle("Choose a task")
        }
    }
}
