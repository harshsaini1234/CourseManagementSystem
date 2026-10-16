const API_URL = "/courses";

const courseForm = document.getElementById("courseForm");
const courseIdInput = document.getElementById("courseId");
const nameInput = document.getElementById("name");
const descriptionInput = document.getElementById("description");
const priceInput = document.getElementById("price");
const courseTableBody = document.getElementById("courseTableBody");
const formTitle = document.getElementById("formTitle");
const submitBtn = document.getElementById("submitBtn");
const messageBox = document.getElementById("messageBox");

document.addEventListener("DOMContentLoaded", fetchCourses);

courseForm.addEventListener("submit", function (event) {
    event.preventDefault();

    const courseId = courseIdInput.value;

    const courseData = {
        name: nameInput.value.trim(),
        description: descriptionInput.value.trim(),
        price: Number(priceInput.value)
    };

    if (courseId) {
        updateCourse(courseId, courseData);
    } else {
        createCourse(courseData);
    }
});

async function fetchCourses() {
    try {
        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Failed to fetch courses");
        }

        const courses = await response.json();
        displayCourses(courses);

    } catch (error) {
        showMessage(error.message, "error");
    }
}

function displayCourses(courses) {
    courseTableBody.innerHTML = "";

    if (courses.length === 0) {
        courseTableBody.innerHTML = `
            <tr>
                <td colspan="5" style="text-align:center;">No courses found</td>
            </tr>
        `;
        return;
    }

    courses.forEach(course => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${course.id}</td>
            <td>${course.name}</td>
            <td>${course.description}</td>
            <td>₹${course.price}</td>
            <td>
                <button class="edit-btn" onclick="editCourse(${course.id}, '${escapeText(course.name)}', '${escapeText(course.description)}', ${course.price})">
                    Edit
                </button>
                <button class="delete-btn" onclick="deleteCourse(${course.id})">
                    Delete
                </button>
            </td>
        `;

        courseTableBody.appendChild(row);
    });
}

async function createCourse(courseData) {
    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(courseData)
        });

        if (!response.ok) {
            await handleErrorResponse(response);
            return;
        }

        showMessage("Course created successfully", "success");
        resetForm();
        fetchCourses();

    } catch (error) {
        showMessage(error.message, "error");
    }
}

async function updateCourse(courseId, courseData) {
    try {
        const response = await fetch(`${API_URL}/${courseId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(courseData)
        });

        if (!response.ok) {
            await handleErrorResponse(response);
            return;
        }

        showMessage("Course updated successfully", "success");
        resetForm();
        fetchCourses();

    } catch (error) {
        showMessage(error.message, "error");
    }
}

function editCourse(id, name, description, price) {
    courseIdInput.value = id;
    nameInput.value = name;
    descriptionInput.value = description;
    priceInput.value = price;

    formTitle.innerText = "Update Course";
    submitBtn.innerText = "Update Course";

    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
}

async function deleteCourse(id) {
    const confirmDelete = confirm("Are you sure you want to delete this course?");

    if (!confirmDelete) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            await handleErrorResponse(response);
            return;
        }

        showMessage("Course deleted successfully", "success");
        fetchCourses();

    } catch (error) {
        showMessage(error.message, "error");
    }
}

function resetForm() {
    courseIdInput.value = "";
    nameInput.value = "";
    descriptionInput.value = "";
    priceInput.value = "";

    formTitle.innerText = "Add New Course";
    submitBtn.innerText = "Add Course";
}

async function handleErrorResponse(response) {
    const errorData = await response.json();

    if (errorData.fieldErrors) {
        const messages = Object.values(errorData.fieldErrors).join(", ");
        showMessage(messages, "error");
        return;
    }

    if (errorData.message) {
        showMessage(errorData.message, "error");
        return;
    }

    showMessage("Something went wrong", "error");
}

function showMessage(message, type) {
    messageBox.innerText = message;
    messageBox.className = `message ${type}`;

    setTimeout(() => {
        messageBox.className = "message";
        messageBox.innerText = "";
    }, 4000);
}

function escapeText(text) {
    return String(text)
        .replace(/\\/g, "\\\\")
        .replace(/'/g, "\\'")
        .replace(/"/g, "&quot;");
}