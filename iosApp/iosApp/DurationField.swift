import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

struct DurationField: View {
    @StateViewModel var formViewModel = FormViewModel()
    @Binding var duration: TimeInterval

    var body: some View {
        VStack{
            Text("Duration")
                .padding(.top, 10)
            
            HStack {
                Button(action: {
                    formViewModel.setSelectedDuration(duration: max(0, formViewModel.selectedDuration - 60))
                    duration = max(0, formViewModel.selectedDuration - 60)
                }) {
                    Image(systemName: "minus")
                        .padding()
                        .foregroundColor(.black)
                }
                .padding(.leading, 40)
                .buttonStyle(.borderless)

                Spacer()
                Text(timeString(from: TimeInterval(formViewModel.selectedDuration)))
                    .font(.largeTitle)
                    .foregroundColor(.gray)
                    .padding()
                    .background(
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(.black, lineWidth: 1)
                    )
                Spacer()
                Button(action: {
                    formViewModel.setSelectedDuration(duration: formViewModel.selectedDuration + 60)
                    duration = formViewModel.selectedDuration + 60
                }) {
                    Image(systemName: "plus")
                        .padding()
                        .foregroundColor(.black)
                }
                .padding(.trailing, 40)
                .buttonStyle(.borderless)
            }
            .padding(.bottom, 10)
            
            HStack {
                DurationAdjustButton(label: "+0:15", adjustment: 15 * 60, duration: $duration).environmentObject(formViewModel)
                DurationAdjustButton(label: "+0:30", adjustment: 30 * 60, duration: $duration).environmentObject(formViewModel)
                DurationAdjustButton(label: "+1:00", adjustment: 60 * 60, duration: $duration).environmentObject(formViewModel)
                DurationAdjustButton(label: "+8:00", adjustment: 8 * 60 * 60, duration: $duration).environmentObject(formViewModel)
            }
            .padding()
        }
    }

    private func timeString(from interval: TimeInterval) -> String {
        let hours = Int(interval) / 3600
        let minutes = (Int(interval) % 3600) / 60
        return String(format: "%02d:%02d", hours, minutes)
    }
}

