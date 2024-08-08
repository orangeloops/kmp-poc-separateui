import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

struct ProjectListView: View {
    @StateViewModel var formViewModel = FormViewModel()
    let projects = Task().getProjects()

    var body: some View {
        TextField("Search by project name", text: .constant(""))
            .padding()
            .background(Color(.systemGray6))
            .cornerRadius(10)
            .padding()
        
        List(projects, id: \.self) { project in
            HStack {
                Text(project)
                Spacer()
                if project == formViewModel.selectedProject {
                    Image(systemName: "checkmark")
                        .foregroundColor(.green)
                }
            }
            .contentShape(Rectangle())
            .onTapGesture {
                formViewModel.setSelectedProject(project: project)
            }
        }
        .navigationTitle("Choose a project")
    }
}
