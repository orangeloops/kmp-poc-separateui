import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

struct ClientListView: View {
    @StateViewModel var formViewModel = FormViewModel()
    let clients = Task().getClients()

    var body: some View {
        VStack {
            TextField("Search by client name", text: .constant(""))
                .padding()
                .background(Color(.systemGray6))
                .cornerRadius(10)
                .padding()

            List(clients, id: \.self) { client in
                HStack {
                    Text(client)
                    Spacer()
                    if client == formViewModel.selectedClient {
                        Image(systemName: "checkmark")
                            .foregroundColor(.green)
                    }
                }
                .contentShape(Rectangle())
                .onTapGesture {
                    formViewModel.setSelectedClient(client: client)
                }
            }
            .navigationTitle("Choose a client")
        }
    }
}
